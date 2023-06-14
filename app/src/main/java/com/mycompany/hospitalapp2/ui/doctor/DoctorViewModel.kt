package com.mycompany.hospitalapp2.ui.doctor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mycompany.hospitalapp2.data.model.Doctor
import com.mycompany.hospitalapp2.data.model.Service
import com.mycompany.hospitalapp2.data.model.TimeAppointment
import com.mycompany.hospitalapp2.data.repository.DoctorRepository
import com.mycompany.hospitalapp2.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DoctorViewModel @Inject constructor(
    val repository: DoctorRepository
): ViewModel() {

    private val _doctors = MutableLiveData< UiState<List<Doctor>>>()
    val doctor: LiveData<UiState<List<Doctor>>>
        get() = _doctors

    private val _services = MutableLiveData< UiState<List<Service>>>()
    val service: LiveData<UiState<List<Service>>>
        get() = _services

    private val _getDoctorName = MutableLiveData< UiState<MutableList<String?>>>()
    val getDoctorName: MutableLiveData<UiState<MutableList<String?>>>
        get() = _getDoctorName

    private val _times = MutableLiveData< UiState<List<TimeAppointment>>>()
    val timesApp: LiveData<UiState<List<TimeAppointment>>>
        get() = _times


    fun getDoctor(service: String){
        _doctors.value = UiState.Loading
        repository.getDoctor(serviceName = service) { _doctors.value = it }
    }
    fun getDoctorRole(){
        _services.value = UiState.Loading
        repository.getDoctorRole { _services.value = it }
    }
    fun getDoctorName() {
        _getDoctorName.value = UiState.Loading
        repository.getDoctorName { _getDoctorName.value = it }
    }

    fun getDoctorTime(doctor: String) {
        _times.value = UiState.Loading
        repository.getDoctorTime(docId = doctor) { _times.value = it }
    }
}