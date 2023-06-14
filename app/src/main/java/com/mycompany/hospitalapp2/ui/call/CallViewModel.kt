package com.mycompany.hospitalapp.ui.call

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mycompany.hospitalapp2.data.model.Call
import com.mycompany.hospitalapp2.data.model.CallService
import com.mycompany.hospitalapp2.data.repository.CallRepository
import com.mycompany.hospitalapp2.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CallViewModel @Inject constructor(val repository: CallRepository): ViewModel()  {

    private val _calls = MutableLiveData< UiState<List<Call>>>()
    val call: LiveData<UiState<List<Call>>>
        get() = _calls

    private val _addCall = MutableLiveData<UiState<Pair<Call,String>>>()
    val addCall: LiveData<UiState<Pair<Call, String>>>
        get() = _addCall

    private val _updateCall = MutableLiveData<UiState<String>>()
    val updateCall: LiveData<UiState<String>>
        get() = _updateCall

    private val _deleteCall = MutableLiveData<UiState<String>>()
    val deleteCall: LiveData<UiState<String>>
        get() = _deleteCall

    private val _services = MutableLiveData< UiState<List<CallService>>>()
    val service: LiveData<UiState<List<CallService>>>
        get() = _services

    fun getCalls(){
        _calls.value = UiState.Loading
        repository.getCalls { _calls.value = it }
    }
    fun addCalls (call: Call){
        _addCall.value = UiState.Loading
        repository.addCall(call) { _addCall.value = it }
    }
    fun updateCall(call: Call){
        _updateCall.value = UiState.Loading
        repository.updateCall(call) { _updateCall.value = it }
    }
    fun deleteCall(call: Call){
        _deleteCall.value = UiState.Loading
        repository.deleteCall(call) { _deleteCall.value = it }
    }

    fun getCallService(){
        _services.value = UiState.Loading
        repository.getCallService { _services.value = it }
    }
}