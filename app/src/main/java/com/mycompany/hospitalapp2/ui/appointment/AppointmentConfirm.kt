package com.mycompany.hospitalapp2.ui.appointment

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mycompany.hospitalapp2.R
import com.mycompany.hospitalapp2.data.model.*
import com.mycompany.hospitalapp2.databinding.FragmentAppointmentConfirmBinding
import com.mycompany.hospitalapp2.ui.auth.AuthViewModel
import com.mycompany.hospitalapp2.ui.doctor.DoctorViewModel
import com.mycompany.hospitalapp2.util.UiState
import com.mycompany.hospitalapp2.util.hide
import com.mycompany.hospitalapp2.util.show
import com.mycompany.hospitalapp2.util.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class AppointmentConfirm : Fragment() {

    val TAG: String = "AppointmentConfirmFragment"
    lateinit var binding: FragmentAppointmentConfirmBinding
    val viewModel: DoctorViewModel by viewModels()
    val authViewModel: AuthViewModel by viewModels()
    val appointmentViewModel: AppointmentViewModel by viewModels()
    var objDoctor: Doctor? = null
    var objService: Service? = null
    var objAppointment: Appointment? = null
    var objTimeAppointment: TimeAppointment? = null
    var list: MutableList<User> = arrayListOf()
    var patientInfo: MutableList<User> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (this::binding.isInitialized) {
            return binding.root
        } else {
            binding = FragmentAppointmentConfirmBinding.inflate(layoutInflater)
            return binding.root
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel.getUserName()

        objDoctor = arguments?.getParcelable("doctor",)
        objDoctor?.let { doctor ->
            binding.doctorName.setText(doctor.name)
            binding.doctorCabinet.setText(doctor.cabinet)
            binding.doctorRole.setText(doctor.role)
        }?: run {
        }

        objTimeAppointment = arguments?.getParcelable("time",)
        objTimeAppointment?.let { time ->
            binding.doctorDate.setText(time.one)
        } ?: run {
        }

        observer()

        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.close.setOnClickListener {
            findNavController().navigate(R.id.action_appointmentConfirm_to_appointmentListingFragment)
        }
        binding.buttonMakeAppointment.setOnClickListener{
            onDonePressed()
        }
    }

    private fun observer() {
        authViewModel.user.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBar.show()
                }
                is UiState.Failure -> {
                    binding.progressBar.hide()
                    toast(state.error)
                }
                is UiState.Success -> {
                    binding.progressBar.hide()
                    //toast(state.data.toString())
                    patientInfo = state.data.toMutableList()
                    val firstUser: User? = patientInfo.firstOrNull() // Получить первого пользователя из списка или null, если список пуст
                    val namePatient: String? = firstUser?.name
                    binding.patientName.setText(namePatient)
                }
            }
        }
        appointmentViewModel.addAppointment.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBar.show()
                }
                is UiState.Failure -> {
                    binding.progressBar.hide()
                    toast(state.error)
                }
                is UiState.Success -> {
                    binding.progressBar.hide()
                    toast(state.data.second)
                    objAppointment = state.data.first

                }
            }
        }
    }
    private fun getAppointment(): Appointment {
        return Appointment(
            id = objAppointment?.id ?: "",
            patient = binding.patientName.text.toString(),
            service = binding.doctorRole.text.toString(),
            cabinet = binding.doctorCabinet.text.toString(),
            doctor = binding.doctorName.text.toString(),
            date = binding.doctorDate.text.toString(),
            created = Date()
        )
    }
    private fun onDonePressed() {
        if (objAppointment == null) {
            appointmentViewModel.addAppointment(getAppointment())

            val coroutineScope = CoroutineScope(Dispatchers.Main)
            coroutineScope.launch {
                delay(500) // Задержка в миллисекундах (здесь 2 секунды)

                // Вызов перехода на следующий фрагмент
                findNavController().navigate(R.id.action_appointmentConfirm_to_appointmentHistory)
            }
        }
    }
}