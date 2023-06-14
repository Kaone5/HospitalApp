package com.mycompany.hospitalapp2.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mycompany.hospitalapp2.R
import com.mycompany.hospitalapp2.data.model.User
import com.mycompany.hospitalapp2.databinding.FragmentRegisterBinding
import com.mycompany.hospitalapp2.util.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegisterFragment : Fragment() {
    val TAG: String = "RegisterFragment"
    lateinit var binding: FragmentRegisterBinding
    val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        binding.buttonRegister.setOnClickListener{
            if (validation()){
                viewModel.register(
                    email = binding.textLogin.text.toString(),
                    password = binding.textPassword.text.toString(),
                    user = getUserObj()
                )
            }
        }
        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
    }
    fun observer(){
        viewModel.register.observe(viewLifecycleOwner) { state ->
            when(state){
                is UiState.Loading -> {
                    binding.buttonRegister.setText("")
                    binding.registerProgress.show()
                }
                is UiState.Failure -> {
                    binding.buttonRegister.setText("Зарегистрироваться")
                    binding.registerProgress.hide()
                    toast(state.error)
                }
                is UiState.Success -> {
                    binding.buttonRegister.setText("Зарегистрироваться")
                    binding.registerProgress.hide()
                    toast(state.data)
                    findNavController().navigate(R.id.action_registerFragment_to_appointment_navigation)
                }
            }
        }
    }
    fun getUserObj(): User {
        return User(
            id = "",
            name = binding.inputName.text.toString(),
            phone = binding.inputPhone.text.toString(),
            email = binding.textLogin.text.toString(),
            role = "user",

            )
    }
    fun validation(): Boolean {
        var isValid = true

        if (binding.inputName.text.isNullOrEmpty()){
            isValid = false
            toast("Введите ФИО пользователя")
        }

        if (binding.inputPhone.text.isNullOrEmpty()){
            isValid = false
            toast("Введите номер телефона")
        }else {
            if (binding.inputPhone.text.toString().length != 10) {
                isValid = false
                toast("Неправильный ввод номера телефона")
            }
        }

        if (binding.textLogin.text.isNullOrEmpty()){
            isValid = false
            toast(getString(R.string.enter_email))
        }else{
            if (!binding.textLogin.text.toString().isValidEmail()){
                isValid = false
                toast(getString(R.string.invalid_email))
            }
        }
        if (binding.textPassword.text.isNullOrEmpty()){
            isValid = false
            toast(getString(R.string.enter_password))
        }else{
            if (binding.textPassword.text.toString().length < 6){
                isValid = false
                toast(getString(R.string.invalid_password))
            }
            if (binding.textPassword.text.toString() != binding.textRepeatPassword.text.toString()){
                isValid = false
                toast(getString(R.string.not_matching_passwords))
            }
        }
        return isValid
    }
}