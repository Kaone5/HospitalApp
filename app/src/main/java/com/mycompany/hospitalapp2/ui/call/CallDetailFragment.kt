package com.mycompany.hospitalapp.ui.call

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mycompany.hospitalapp2.R
import com.mycompany.hospitalapp2.data.model.*
import com.mycompany.hospitalapp2.databinding.FragmentAppointmentListingBinding
import com.mycompany.hospitalapp2.databinding.FragmentCallDetailBinding
import com.mycompany.hospitalapp2.ui.auth.AuthViewModel
import com.mycompany.hospitalapp2.util.UiState
import com.mycompany.hospitalapp2.util.hide
import com.mycompany.hospitalapp2.util.show
import com.mycompany.hospitalapp2.util.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class CallDetailFragment: Fragment() {
    val TAG: String = "CallDetailFragment"
    lateinit var binding: FragmentCallDetailBinding
    var objService: CallService? = null
    var objCall: Call? = null
    val authViewModel: AuthViewModel by viewModels()
    val callViewModel: CallViewModel by viewModels()
    var patientInfo: MutableList<User> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (this::binding.isInitialized){
            return binding.root
        }else {
            binding = FragmentCallDetailBinding.inflate(layoutInflater)
            return binding.root
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authViewModel.getUserName()
        binding.callServiceButton.setOnClickListener{
            findNavController().navigate(R.id.action_callDetailFragment_to_callServiceListingFragment)
        }

        objService = arguments?.getParcelable("servicecall",)
        if (objService != null) {
            objService = arguments?.getParcelable("servicecall",)
            objService?.let { service ->
                binding.callServiceText.setText(service.name)
            } ?: run {
            }
            
        }
        observer()
        binding.button2.setOnClickListener{
            if (validation()) {
                onDonePressed()
            }
        }
        binding.back.setOnClickListener{
            findNavController().navigate(R.id.action_callDetailFragment_to_callListingFragment2)
        }
    }
    private fun observer() {
        authViewModel.user.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBar.show()
                }
                is UiState.Failure -> {
                    binding.progressBar.hide()
                    toast(state.error)
                }
                is UiState.Success -> {
                    binding.progressBar.hide()
                    //toast(state.data.toString())
                    patientInfo = state.data.toMutableList()
                    val firstUser: User? =
                        patientInfo.firstOrNull() // Получить первого пользователя из списка или null, если список пуст
                    val namePatient: String? = firstUser?.name
                    val phonePatient: String? =firstUser?.phone
                    binding.textPatient.setText(namePatient)
                    binding.textPhone.setText(phonePatient)
                }
            }
        }
        callViewModel.addCall.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBar.show()
                }
                is UiState.Failure -> {
                    binding.progressBar.hide()
                    toast(state.error)
                }
                is UiState.Success -> {
                    binding.progressBar.hide()
                    toast(state.data.second)
                    objCall = state.data.first

                }
            }
        }
    }

    private fun getCall(): Call {
        return Call(
            id = objCall?.id ?: "",
            patient = binding.textPatient.text.toString(),
            service = binding.callServiceText.text.toString(),
            date = binding.callDate.selectedItem.toString(),
            address = binding.textAddress.text.toString(),
            phone = binding.textPhone.text.toString(),
            info = binding.taskEt.text.toString(),
            created = Date()
        )
    }
    private fun onDonePressed() {
        if (objCall == null) {
            callViewModel.addCalls(getCall())

            val coroutineScope = CoroutineScope(Dispatchers.Main)
            coroutineScope.launch {
                delay(500) // Задержка в миллисекундах (здесь 2 секунды)

                // Вызов перехода на следующий фрагмент
                findNavController().navigate(R.id.action_callDetailFragment_to_callHistory)
            }
        }
    }
    private fun validation(): Boolean {
        var isValid = true
        if (binding.textPatient.text.toString().isNullOrEmpty() ||
            binding.textPhone.text.toString().isNullOrEmpty() ||
            binding.textAddress.text.toString().isNullOrEmpty() ||
            binding.callServiceText.text.toString().isNullOrEmpty() || binding.callDate.selectedItem.toString() == "Выберите дату приема"
        ) {
            isValid = false
            toast(("Пустые поля!"))
        }
        if (
            binding.textPhone.text.toString().length != 10){
            isValid = false
            toast(("Неправильный номер телефона!"))
        }
        return isValid
    }
}