package com.mycompany.hospitalapp2.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mycompany.hospitalapp2.data.model.Appointment
import com.mycompany.hospitalapp2.util.FirestoreTables
import com.mycompany.hospitalapp2.util.UiState


class AppointmentRepositoryImp(val database: FirebaseFirestore): AppointmentRepository {
    override fun getAppointments(result: (UiState<List<Appointment>>) -> Unit) {
        database.collection(FirestoreTables.APPOINTMENT).orderBy("created", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {
                val appointments = arrayListOf<Appointment>()
                for (document in it){
                    val appointment = document.toObject(Appointment::class.java)
                    appointments.add(appointment) }
                result.invoke(
                    UiState.Success(appointments)) }
            .addOnFailureListener(){
                result.invoke(
                    UiState.Failure(it.localizedMessage)) }
    }

    override fun addAppointment(appointment: Appointment, result: (UiState<Pair<Appointment, String>>) -> Unit) {
        val document = database.collection(FirestoreTables.APPOINTMENT).document()
        appointment.id = document.id
        document
            .set(appointment)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success(Pair(appointment,"Запись успешно добавлена"))) }
            .addOnFailureListener(){
                result.invoke(
                    UiState.Failure(it.localizedMessage)) }
    }

    override fun updateAppointment(appointment: Appointment, result: (UiState<String>) -> Unit) {
        val document = database.collection(FirestoreTables.APPOINTMENT).document(appointment.id)
        document
            .set(appointment)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success("Информация о записи успешно обновлена")) }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage)) }
    }

    override fun deleteAppointment(appointment: Appointment, result: (UiState<String>) -> Unit) {
        database.collection(FirestoreTables.APPOINTMENT).document(appointment.id)
            .delete()
            .addOnSuccessListener {
                result.invoke(UiState.Success("Запись успешно удалена")) }
            .addOnFailureListener { e ->
                result.invoke(UiState.Failure(e.message)) }
    }
}