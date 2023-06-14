package com.mycompany.hospitalapp2.ui.appointment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mycompany.hospitalapp2.data.model.Appointment
import com.mycompany.hospitalapp2.data.repository.AppointmentRepository
import com.mycompany.hospitalapp2.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor(val repository: AppointmentRepository): ViewModel() {

    private val _appointments = MutableLiveData<UiState<List<Appointment>>>()
    val appointment: LiveData<UiState<List<Appointment>>>
        get() = _appointments

    private val _addAppointment = MutableLiveData<UiState<Pair<Appointment,String>>>()
    val addAppointment: LiveData<UiState<Pair<Appointment, String>>>
        get() = _addAppointment

    private val _updateAppointment = MutableLiveData<UiState<String>>()
    val updateAppointment: LiveData<UiState<String>>
        get() = _updateAppointment

    private val _deleteAppointment = MutableLiveData<UiState<String>>()
    val deleteAppointment: LiveData<UiState<String>>
        get() = _deleteAppointment

    fun getAppointments(){
        _appointments.value = UiState.Loading
        repository.getAppointments { _appointments.value = it }
    }
    fun addAppointment(appointment: Appointment){
        _addAppointment.value = UiState.Loading
        repository.addAppointment(appointment) { _addAppointment.value = it }
    }
    fun updateAppointment(appointment: Appointment){
        _updateAppointment.value = UiState.Loading
        repository.updateAppointment(appointment) { _updateAppointment.value = it }
    }
    fun deleteAppointment(appointment: Appointment){
        _deleteAppointment.value = UiState.Loading
        repository.deleteAppointment(appointment) { _deleteAppointment.value = it }
    }
}