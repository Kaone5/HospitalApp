package com.mycompany.hospitalapp2.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.mycompany.hospitalapp2.data.model.Doctor
import com.mycompany.hospitalapp2.data.model.Service
import com.mycompany.hospitalapp2.data.model.TimeAppointment
import com.mycompany.hospitalapp2.util.FirestoreTables
import com.mycompany.hospitalapp2.util.UiState
import java.util.ArrayList

class DoctorRepositoryImp(
    val database: FirebaseFirestore
    ): DoctorRepository {
    override fun getDoctor(serviceName: String,result: (UiState<List<Doctor>>) -> Unit) {
        database.collection(FirestoreTables.DOCTOR).whereEqualTo("role",  serviceName)
            .orderBy("name", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener {
                val doctors = arrayListOf<Doctor>()
                for (document in it){
                    val doctor = document.toObject(Doctor::class.java)
                    doctors.add(doctor)
                }
                result.invoke(
                    UiState.Success(doctors)
                )
            }
            .addOnFailureListener(){
                result.invoke(
                    UiState.Failure(it.localizedMessage)
                )
            }
    }


    override fun getDoctorName(result: (UiState<MutableList<String?>>) -> Unit) {
        database.collection(FirestoreTables.DOCTOR)
            .get()
            .addOnSuccessListener(OnSuccessListener<QuerySnapshot?> {
                val docName: MutableList<String?> = ArrayList()
                for (document in it) {
                    val doctorName = document.getString("name")
                    docName.add(doctorName)
                }
                result.invoke(
                    UiState.Success(docName)
                )
            })
            .addOnFailureListener(){
                result.invoke(
                    UiState.Failure(it.localizedMessage)
                )
            }
    }

    override fun getDoctorTime(docId: String,result: (UiState<List<TimeAppointment>>) -> Unit) {
        database.collection("doctor")
            .get()
            .addOnSuccessListener { documents ->
                val times = arrayListOf<TimeAppointment>()
                //for (document in documents) {
                    //val doctorId = document.id
                    Log.d(TAG, docId)
                    database.collection("doctor").document(docId).collection("time_true")
                        .orderBy("id", Query.Direction.ASCENDING)
                        .get()
                        .addOnSuccessListener {
                            val times = arrayListOf<TimeAppointment>()
                            for (document in it) {
                                val timeApp = document.toObject(TimeAppointment::class.java)
                                times.add(timeApp)
                            }
                            result.invoke(
                                UiState.Success(times)
                            )
                        }
                        .addOnFailureListener() {
                            result.invoke(
                                UiState.Failure(it.localizedMessage)
                            )
                        }
                //}
            }
    }
    override fun getDoctorRole(result: (UiState<List<Service>>) -> Unit) {
        database.collection(FirestoreTables.SERVICE)
            .orderBy("name", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener {
                val services = arrayListOf<Service>()
                for (document in it){
                    val service = document.toObject(Service::class.java)
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

/*
    override fun getDoctorTime(result: (UiState<List<TimeAppointment>>) -> Unit) {
        database.collection("doctor")
            .get()
            .addOnSuccessListener { documents ->
                val times = arrayListOf<TimeAppointment>()
                for (document in documents) {
                    val doctorId = document.id
                    database.collection("doctor").document(doctorId).collection("time_true")
                        .get()
                        .addOnSuccessListener { snapshot ->
                            val timeApp = snapshot.toObject(TimeAppointment::class.java)
                            if (timeApp != null) {
                                times.add(timeApp)
                            }
                            if (times.size == documents.size()) {
                                result.invoke(UiState.Success(times))
                            }
                        }
                        .addOnFailureListener { exception ->
                            result.invoke(UiState.Failure(exception.localizedMessage))
                        }
                }
            }
            .addOnFailureListener { exception ->
                result.invoke(UiState.Failure(exception.localizedMessage))
            }
    }

*/

    /*
    override fun getDoctorTime(result: (UiState<List<TimeAppointment>>) -> Unit) {
        //val doc = database.collection("doctor")
        //doc.document("uiE9NiVSI3ViSAAekDoK").collection("time")
        val auth = FirebaseAuth.getInstance()
        val cu = auth.currentUser!!.uid
        database.collection("doctor").document(cu!!).collection("time").document("time_true")
        //database.collection("time")
            .get()
            .addOnSuccessListener {
                val times = arrayListOf<TimeAppointment>()
                for (document in it){
                    val doctor = document.toObject(Doctor::class.java)
                    times.add(times)
                }
                result.invoke(
                    UiState.Success(doctors)
                )
            }
            .addOnFailureListener(){
                result.invoke(
                    UiState.Failure(it.localizedMessage)
                )
            }

            .get()
            .addOnSuccessListener(OnSuccessListener<QuerySnapshot?> {
                val docTime: MutableList<String?> = ArrayList()
                for (document in it) {
                    val doctorTime = document.getString("time_true")
                    docTime.add(doctorTime)
                }
                result.invoke(
                    UiState.Success(docTime)
                )
            })
            .addOnFailureListener(){
                result.invoke(
                    UiState.Failure(it.localizedMessage)
                )
            }


    }

    override fun getDoctorTime(result: (UiState<List<TimeAppointment>>) -> Unit) {
    val auth = FirebaseAuth.getInstance()
    val cu = auth.currentUser!!.uid
    database.collection("doctor").document(cu!!).collection("time").document("time_true")

        .get()
            .addOnSuccessListener {
                val times = arrayListOf<TimeAppointment>()
                for (document in it.documents){
                    val timeApp = document.toObject(TimeAppointment::class.java)
                    times.add(timeApp)
                }
                result.invoke(
                    UiState.Success(times)
                )
            }
            .addOnFailureListener(){
                result.invoke(
                    UiState.Failure(it.localizedMessage)
                )
            }

    }

 */

}