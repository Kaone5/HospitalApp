package com.mycompany.hospitalapp2.data.repository

import com.mycompany.hospitalapp2.data.model.Appointment
import com.mycompany.hospitalapp2.util.UiState

interface AppointmentRepository {
    fun getAppointments(result: (UiState<List<Appointment>>) -> Unit)
    fun addAppointment(appointment: Appointment, result: (UiState<Pair<Appointment,String>>) -> Unit)
    fun updateAppointment(appointment: Appointment, result: (UiState<String>) -> Unit)
    fun deleteAppointment(appointment: Appointment, result: (UiState<String>) -> Unit)
}