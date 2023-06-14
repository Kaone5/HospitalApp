package com.mycompany.hospitalapp2.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mycompany.hospitalapp2.data.model.User
import com.mycompany.hospitalapp2.data.repository.AuthRepository
import com.mycompany.hospitalapp2.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(val repository: AuthRepository): ViewModel() {

    private val _register = MutableLiveData<UiState<String>>()
    val register: LiveData<UiState<String>>
        get() = _register

    private val _login = MutableLiveData<UiState<String>>()
    val login: LiveData<UiState<String>>
        get() = _login

    private val _users = MutableLiveData< UiState<List<User>>>()
    val user: LiveData<UiState<List<User>>>
        get() = _users

    private val _updateUser = MutableLiveData<UiState<String>>()
    val updateUser: LiveData<UiState<String>>
        get() = _updateUser

    fun register(
        email: String,
        password: String,
        user: User
    ) {
        _register.value = UiState.Loading
        repository.registerUser(
            email = email,
            password = password,
            user = user
        ) { _register.value = it }
    }
    fun login(
        email: String,
        password: String
    ) {
        _login.value = UiState.Loading
        repository.loginUser(
            email,
            password
        ){ _login.value = it }
    }

    fun logout(result: () -> Unit){
        repository.logout(result)
    }

    fun getSession(result: (User?) -> Unit){
        repository.getSession(result)
    }

    fun getUserName(){
        _users.value = UiState.Loading
        repository.getUserName { _users.value = it }
    }

    fun updateUser(user: User){
        _updateUser.value = UiState.Loading
        repository.updateUser(user) { _updateUser.value = it }
    }

}