package com.mycompany.hospitalapp2.ui.appointment

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
import com.mycompany.hospitalapp2.data.model.Doctor
import com.mycompany.hospitalapp2.databinding.FragmentAppointmentDateBinding
import com.mycompany.hospitalapp2.ui.doctor.DoctorViewModel
import com.mycompany.hospitalapp2.util.UiState
import com.mycompany.hospitalapp2.util.hide
import com.mycompany.hospitalapp2.util.show
import com.mycompany.hospitalapp2.util.toast
import dagger.hilt.android.AndroidEntryPoint


private const val ARG_PARAM1 = "param1"

@AndroidEntryPoint
class AppointmentDateFragment : Fragment() {

    val TAG: String = "AppointmentDateFragment"
    lateinit var binding: FragmentAppointmentDateBinding
    val viewModel: DoctorViewModel by viewModels()
    var objDoctor: Doctor? = null //исправить
    //var objService: String = "" //исправить
    val adapter by lazy {
        AppointmentDateFragmentAdapter(
            onItemClicked = { pos, item ->
                findNavController().navigate(R.id.action_appointmentDateFragment_to_appointmentConfirm,Bundle().apply {
                    putParcelable("time",item)
                    putParcelable("doctor", objDoctor)

                })
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (this::binding.isInitialized){
            return binding.root
        }else {
            binding = FragmentAppointmentDateBinding.inflate(layoutInflater)
            return binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //updateUI()
        observer()
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        binding.timeListing.layoutManager = staggeredGridLayoutManager
        binding.timeListing.adapter = adapter
        objDoctor = arguments?.getParcelable("doctor",)
        objDoctor?.let { doctor ->
            binding.nameText.setText(doctor.name)
            binding.roleText.setText(doctor.role)
            var docId = doctor.id
            viewModel.getDoctorTime(docId)
        } ?: run {
        }

        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.close.setOnClickListener {
            findNavController().navigate(R.id.action_appointmentDateFragment_to_appointmentListingFragment)
        }
    }

    private fun observer(){
        viewModel.timesApp.observe(viewLifecycleOwner){ state ->
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

}