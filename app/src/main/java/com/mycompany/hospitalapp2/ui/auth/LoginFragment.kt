package com.mycompany.hospitalapp2.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mycompany.hospitalapp2.R
import com.mycompany.hospitalapp2.databinding.FragmentLoginBinding
import com.mycompany.hospitalapp2.util.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment() {
    val TAG: String = "LoginFragment"
    lateinit var binding: FragmentLoginBinding
    val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        binding.buttonEnter.setOnClickListener {
            if (validation()) {
                viewModel.login(
                    email = binding.enterLogin.text.toString(),
                    password = binding.enterPassword.text.toString()) }
        }
        binding.buttonReg.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
    fun observer(){
        viewModel.login.observe(viewLifecycleOwner) { state ->
            when(state){
                is UiState.Loading -> {
                    binding.buttonEnter.setText("")
                    binding.loginProgress.show()
                }
                is UiState.Failure -> {
                    binding.buttonEnter.setText("Войти")
                    binding.loginProgress.hide()
                    toast(state.error)
                }
                is UiState.Success -> {
                    binding.buttonEnter.setText("Войти")
                    binding.loginProgress.hide()
                    toast(state.data)
                    findNavController().navigate(R.id.action_loginFragment_to_appointment_navigation)
                }
            }
        }
    }
    fun validation(): Boolean {
        var isValid = true
        if (binding.enterLogin.text.isNullOrEmpty()){
            isValid = false
            toast(getString(R.string.enter_email))
        }else{
            if (!binding.enterLogin.text.toString().isValidEmail()){
                isValid = false
                toast(getString(R.string.invalid_email))
            }
        }
        if (binding.enterPassword.text.isNullOrEmpty()){
            isValid = false
            toast(getString(R.string.enter_password))
        }else{
            if (binding.enterPassword.text.toString().length < 6){
                isValid = false
                toast(getString(R.string.invalid_password))
            }
        }
        return isValid
    }
    override fun onStart() {
        super.onStart()
        viewModel.getSession { user ->
            if (user != null){
                findNavController().navigate(R.id.action_loginFragment_to_appointment_navigation)
            }
        }
    }
}