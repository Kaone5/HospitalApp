package com.mycompany.hospitalapp2.ui.doctor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mycompany.hospitalapp2.R
import com.mycompany.hospitalapp2.databinding.FragmentDoctorRoleListingBinding
import com.mycompany.hospitalapp2.ui.auth.AuthViewModel
import com.mycompany.hospitalapp2.util.UiState
import com.mycompany.hospitalapp2.util.hide
import com.mycompany.hospitalapp2.util.show
import com.mycompany.hospitalapp2.util.toast
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"

@AndroidEntryPoint
class DoctorRoleListing : Fragment() {

    val TAG: String = "DoctorRoleListingFragment"
    lateinit var binding: FragmentDoctorRoleListingBinding
    val viewModel: DoctorViewModel by viewModels()
    val authViewModel: AuthViewModel by viewModels()
    val adapter by lazy {
        DoctorRoleAdapter(
            onItemClicked = { pos, item ->
                findNavController().navigate(R.id.action_doctorRoleListing_to_doctorNameListing,Bundle().apply {
                    putParcelable("role",item)
                })
            }
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (this::binding.isInitialized){
            return binding.root
        }else {
            binding = FragmentDoctorRoleListingBinding.inflate(layoutInflater)
            return binding.root
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        binding.doctorRoleListing.layoutManager = staggeredGridLayoutManager
        binding.doctorRoleListing.adapter = adapter

        viewModel.getDoctorRole()
        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.close.setOnClickListener {
            findNavController().navigate(R.id.action_doctorRoleListing_to_appointmentListingFragment)
        }
    }
    private fun observer(){
        viewModel.service.observe(viewLifecycleOwner){ state ->
            when(state){
                is UiState.Loading -> {
                    binding.progressBar.show()
                }
                is UiState.Failure ->{
                    binding.progressBar.hide()
                    toast(state.error)
                }
                is UiState.Success ->{
                    binding.progressBar.hide()
                    adapter.updateList(state.data.toMutableList())
                }
            }
        }
    }
    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DoctorRoleListing().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}