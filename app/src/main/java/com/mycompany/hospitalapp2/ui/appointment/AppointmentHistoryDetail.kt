package com.mycompany.hospitalapp2.ui.appointment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mycompany.hospitalapp.ui.call.CallViewModel
import com.mycompany.hospitalapp2.R
import com.mycompany.hospitalapp2.data.model.Appointment
import com.mycompany.hospitalapp2.data.model.Call
import com.mycompany.hospitalapp2.databinding.FragmentAppointmentHistoryBinding
import com.mycompany.hospitalapp2.databinding.FragmentAppointmentHistoryDetailBinding
import com.mycompany.hospitalapp2.databinding.FragmentCallHistoryDetailBinding
import com.mycompany.hospitalapp2.util.UiState
import com.mycompany.hospitalapp2.util.hide
import com.mycompany.hospitalapp2.util.show
import com.mycompany.hospitalapp2.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppointmentHistoryDetail : Fragment() {

    val TAG: String = "AppointmentHistoryDetail"
    lateinit var binding: FragmentAppointmentHistoryDetailBinding
    val viewModel: AppointmentViewModel by viewModels()
    var objAppointment: Appointment? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (this::binding.isInitialized){
            return binding.root
        }else {
            binding = FragmentAppointmentHistoryDetailBinding.inflate(layoutInflater)
            return binding.root
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        updateUI()
        binding.buttonMakeAppointment.setOnClickListener {
            objAppointment?.let { viewModel.deleteAppointment(it) }

        }
    }
    private fun observer() {
        viewModel.deleteAppointment.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBar.show() }
                is UiState.Failure -> {
                    binding.progressBar.hide()
                    toast(state.error) }
                is UiState.Success -> {
                    binding.progressBar.hide()
                    toast(state.data)
                    findNavController().navigateUp() }
            }
        }
    }
    private fun updateUI() {
        objAppointment = arguments?.getParcelable("appointment",)
        objAppointment?.let { call ->
            binding.patientName.setText(call.patient)
            binding.doctorRole.setText(call.service)
            binding.doctorName.setText(call.doctor)
            binding.doctorDate.setText(call.date)
            binding.doctorCabinet.setText(call.cabinet)
            binding.doctorDate.setText(call.date)
        } ?: run {
        }
        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}