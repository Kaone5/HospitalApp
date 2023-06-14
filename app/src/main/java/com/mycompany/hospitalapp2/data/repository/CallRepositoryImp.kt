package com.mycompany.hospitalapp2.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mycompany.hospitalapp2.data.model.Appointment
import com.mycompany.hospitalapp2.data.model.Call
import com.mycompany.hospitalapp2.data.model.CallService
import com.mycompany.hospitalapp2.data.model.Service
import com.mycompany.hospitalapp2.util.FirestoreTables
import com.mycompany.hospitalapp2.util.UiState

class CallRepositoryImp(val database: FirebaseFirestore): CallRepository {
    override fun getCalls(result: (UiState<List<Call>>) -> Unit) {
        database.collection(FirestoreTables.CALL).orderBy("created", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {
                val calls = arrayListOf<Call>()
                for (document in it){
                    val call = document.toObject(Call::class.java)
                    calls.add(call) }
                result.invoke(
                    UiState.Success(calls)) }
            .addOnFailureListener(){
                result.invoke(
                    UiState.Failure(it.localizedMessage)) }
    }

    override fun addCall(call: Call, result: (UiState<Pair<Call, String>>) -> Unit) {
        val document = database.collection(FirestoreTables.CALL).document()
        call.id = document.id
        document
            .set(call)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success(Pair(call,"Вызов успешно добавлен"))) }
            .addOnFailureListener(){
                result.invoke(
                    UiState.Failure(it.localizedMessage)) }
    }

    override fun updateCall(call: Call, result: (UiState<String>) -> Unit) {
        val document = database.collection(FirestoreTables.CALL).document(call.id)
        document
            .set(call)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success("Информация о вызове успешно обновлена")) }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage)) }
    }

    override fun deleteCall(call: Call, result: (UiState<String>) -> Unit) {
        database.collection(FirestoreTables.CALL).document(call.id)
            .delete()
            .addOnSuccessListener {
                result.invoke(UiState.Success("Вызов успешно удален")) }
            .addOnFailureListener { e ->
                result.invoke(UiState.Failure(e.message)) }
    }
    override fun getCallService(result: (UiState<List<CallService>>) -> Unit) {
        database.collection(FirestoreTables.CALLSERVICE)
            .orderBy("name", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener {
                val services = arrayListOf<CallService>()
                for (document in it){
                    val service = document.toObject(CallService::class.java)
                    services.add(service)
                }
                result.invoke(
                    UiState.Success(services)
                )
            }
            .addOnFailureListener(){
                result.invoke(
                    UiState.Failure(it.localizedMessage)
                )
            }

    }

}