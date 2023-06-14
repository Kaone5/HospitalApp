package com.mycompany.hospitalapp2.ui.doctor

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mycompany.hospitalapp2.R
import com.mycompany.hospitalapp2.data.model.Service
import com.mycompany.hospitalapp2.databinding.FragmentDoctorNameListingBinding
import com.mycompany.hospitalapp2.ui.appointment.AppointmentDateFragment
import com.mycompany.hospitalapp2.ui.auth.AuthViewModel
import com.mycompany.hospitalapp2.util.UiState
import com.mycompany.hospitalapp2.util.hide
import com.mycompany.hospitalapp2.util.show
import com.mycompany.hospitalapp2.util.toast
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"

@AndroidEntryPoint
class DoctorNameListing : Fragment() {

    val TAG: String = "DoctorNameListingFragment"
    lateinit var binding: FragmentDoctorNameListingBinding
    val viewModel: DoctorViewModel by viewModels()
    val authViewModel: AuthViewModel by viewModels()
    var objService: Service? = null //исправить
    val adapter by lazy {
        DoctorNameAdapter(
            onItemClicked = { pos, item ->
                findNavController().navigate(R.id.action_doctorNameListing_to_appointmentDateFragment,Bundle().apply {
                    putParcelable("doctor",item)
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
            binding = FragmentDoctorNameListingBinding.inflate(layoutInflater)
            return binding.root
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        binding.doctorListing.layoutManager = staggeredGridLayoutManager
        binding.doctorListing.adapter = adapter

        objService = arguments?.getParcelable("role",)
        objService?.let { service ->
            binding.roleText.setText(service.name)


            var serviceName = service.name
            viewModel.getDoctor(serviceName)
        } ?: run {
        }
        val roleText = binding.roleText.text.toString()

        val appointmentDateFragment = AppointmentDateFragment()
        val bundle = Bundle()
        bundle.putString("roleDoc", roleText)
        appointmentDateFragment.arguments = bundle


        Log.d(ContentValues.TAG, roleText)

        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.close.setOnClickListener {
            findNavController().navigate(R.id.action_doctorNameListing_to_appointmentListingFragment)
        }
    }

    private fun observer(){
        viewModel.doctor.observe(viewLifecycleOwner){ state ->
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

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DoctorNameListing().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)

                }
            }
    }
}