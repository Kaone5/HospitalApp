package com.mycompany.hospitalapp2.ui.call

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mycompany.hospitalapp.ui.call.CallViewModel
import com.mycompany.hospitalapp2.R
import com.mycompany.hospitalapp2.data.model.Call
import com.mycompany.hospitalapp2.data.model.Doctor
import com.mycompany.hospitalapp2.databinding.FragmentCallHistoryBinding
import com.mycompany.hospitalapp2.databinding.FragmentCallHistoryDetailBinding
import com.mycompany.hospitalapp2.util.UiState
import com.mycompany.hospitalapp2.util.hide
import com.mycompany.hospitalapp2.util.show
import com.mycompany.hospitalapp2.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CallHistoryDetail : Fragment() {
    val TAG: String = "CallHistoryDetail"
    lateinit var binding: FragmentCallHistoryDetailBinding
    val viewModel: CallViewModel by viewModels()
    var objCall: Call? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (this::binding.isInitialized){
            return binding.root
        }else {
            binding = FragmentCallHistoryDetailBinding.inflate(layoutInflater)
            return binding.root
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        updateUI()
        binding.buttonDeleteCall.setOnClickListener {
            objCall?.let { viewModel.deleteCall(it) }

        }
    }
    private fun observer() {
        viewModel.deleteCall.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBar.show() }
                is UiState.Failure -> {
                    binding.progressBar.hide()
                    toast(state.error) }
                is UiState.Success -> {
                    binding.progressBar.hide()
                    toast(state.data)
                    findNavController().navigateUp() }
            }
        }
    }
    private fun updateUI() {
        objCall = arguments?.getParcelable("call",)
        objCall?.let { call ->
            binding.patientName.setText(call.patient)
            binding.callServiceText.setText(call.service)
            binding.doctorCabinet.setText(call.address)
            binding.doctorDate.setText(call.date)
            binding.phoneText.setText(call.phone)
            binding.infoText.setText(call.info)
        } ?: run {
            binding.infoHeader.hide()
            binding.infoText.setText("")
        }
        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}