package com.mycompany.hospitalapp2.ui.account

import android.app.DatePickerDialog
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mycompany.hospitalapp2.R
import com.mycompany.hospitalapp2.data.model.Doctor
import com.mycompany.hospitalapp2.data.model.User
import com.mycompany.hospitalapp2.databinding.FragmentAccountBinding
import com.mycompany.hospitalapp2.databinding.FragmentCallListingBinding
import com.mycompany.hospitalapp2.ui.auth.AuthViewModel
import com.mycompany.hospitalapp2.util.UiState
import com.mycompany.hospitalapp2.util.hide
import com.mycompany.hospitalapp2.util.show
import com.mycompany.hospitalapp2.util.toast
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AccountFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    val TAG: String = "AccountFragment"
    lateinit var binding: FragmentAccountBinding
    val authViewModel: AuthViewModel by viewModels()
    var patientInfo: MutableList<User> = arrayListOf()
    var objUser: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (this::binding.isInitialized){
            return binding.root
        }else {
            binding = FragmentAccountBinding.inflate(layoutInflater)
            return binding.root
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authViewModel.getUserName()
        observer()
        binding.appointment.setOnClickListener{
            findNavController().navigate(R.id.action_accountFragment2_to_appointmentListingFragment)
        }
        binding.call.setOnClickListener{
            findNavController().navigate(R.id.action_accountFragment2_to_callListingFragment2)
        }
        binding.editButton.setOnClickListener {
            if (validation()) {
                onDonePressed()
            }
        }
        binding.logout.setOnClickListener {
            authViewModel.logout {
                findNavController().navigate(R.id.action_accountFragment2_to_LoginFragment)
            }
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
                    //val namePatient: String? = firstUser?.name
                    if (firstUser != null) {
                        binding.nameEdit.setText(firstUser.name)
                        binding.birthdateEdit.setText(firstUser.birth_date)
                        binding.policyEdit.setText(firstUser.policy)
                        binding.numberEdit.setText(firstUser.phone)
                        binding.emailEdit.setText(firstUser.email)
                        binding.passportEdit.setText(firstUser.passport)

                        var bdDate = binding.birthdateEdit.text.toString()
                        Log.d(TAG, bdDate)
                        if (bdDate == ""){
                            binding.birthdayButton.show()
                            binding.birthdayButton.setOnClickListener {
                                showDatePickerDialog()
                            }
                        }
                        else{
                            binding.birthdayButton.hide()
                            binding.birthdateEdit.setOnClickListener {
                                showDatePickerDialog()
                            }
                        }
                    }

                }
            }
        }
        authViewModel.updateUser.observe(viewLifecycleOwner) { state ->
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
                    toast(state.data)
                }
            }
        }
    }

    private fun validation(): Boolean {
        var isValid = true
        if (binding.nameEdit.text.toString().isNullOrEmpty() ||
            binding.numberEdit.text.toString().isNullOrEmpty() ||
            binding.emailEdit.text.toString().isNullOrEmpty()
        ) {
            isValid = false
            toast(("Пустые поля!"))
        }
        if (binding.policyEdit.text.toString().length != 16 ||
            binding.passportEdit.text.toString().length != 10 ||
            binding.numberEdit.text.toString().length != 10){
            isValid = false
            toast(("Ошибка ввода!"))
        }
        return isValid
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), this, year, month, day)
        datePickerDialog.show()
    }
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val selectedDate = "$dayOfMonth/${month + 1}/$year"
        binding.birthdateEdit.text = selectedDate
        binding.birthdayButton.hide()
    }

    private fun getUser(): User {
        return User(
            //id = objUser?.id ?: "",
            name = binding.nameEdit.text.toString(),
            birth_date = binding.birthdateEdit.text.toString(),
            policy = binding.policyEdit.text.toString(),
            phone = binding.numberEdit.text.toString(),
            email = binding.emailEdit.text.toString(),
            passport = binding.passportEdit.text.toString()
        )
    }

    private fun onDonePressed() {
        authViewModel.updateUser(getUser())
    }
}