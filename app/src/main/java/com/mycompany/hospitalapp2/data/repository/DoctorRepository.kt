package com.mycompany.hospitalapp2.data.repository

import com.mycompany.hospitalapp2.data.model.Doctor
import com.mycompany.hospitalapp2.data.model.Service
import com.mycompany.hospitalapp2.data.model.TimeAppointment
import com.mycompany.hospitalapp2.util.UiState

interface DoctorRepository{
    fun getDoctor(serviceName: String, result: (UiState<List<Doctor>>) -> Unit)
    fun getDoctorRole(result: (UiState<List<Service>>) -> Unit)
    fun getDoctorName(result: (UiState<MutableList<String?>>) -> Unit)
    fun getDoctorTime( docId: String, result: (UiState<List<TimeAppointment>>) -> Unit)
}