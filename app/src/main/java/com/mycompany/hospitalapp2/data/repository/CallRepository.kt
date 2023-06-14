package com.mycompany.hospitalapp2.data.repository

import com.mycompany.hospitalapp2.data.model.Appointment
import com.mycompany.hospitalapp2.data.model.Call
import com.mycompany.hospitalapp2.data.model.CallService
import com.mycompany.hospitalapp2.data.model.Service
import com.mycompany.hospitalapp2.util.UiState

interface CallRepository {
    fun getCalls(result: (UiState<List<Call>>) -> Unit)
    fun addCall(call: Call, result: (UiState<Pair<Call, String>>) -> Unit)
    fun updateCall(call: Call, result: (UiState<String>) -> Unit)
    fun deleteCall(call: Call, result: (UiState<String>) -> Unit)
    fun getCallService(result: (UiState<List<CallService>>) -> Unit)
}