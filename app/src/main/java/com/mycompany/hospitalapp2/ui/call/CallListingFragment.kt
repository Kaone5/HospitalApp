package com.mycompany.hospitalapp.ui.call

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mycompany.hospitalapp2.R
import com.mycompany.hospitalapp2.databinding.FragmentAppointmentListingBinding
import com.mycompany.hospitalapp2.databinding.FragmentCallListingBinding
import com.mycompany.hospitalapp2.ui.appointment.AppointmentViewModel
import com.mycompany.hospitalapp2.util.UiState
import com.mycompany.hospitalapp2.util.hide
import com.mycompany.hospitalapp2.util.show
import com.mycompany.hospitalapp2.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CallListingFragment: Fragment() {
    val TAG: String = "CallListingFragment"
    lateinit var binding: FragmentCallListingBinding
    val viewModel: AppointmentViewModel by viewModels() //исправить на callViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (this::binding.isInitialized){
            return binding.root
        }else {
            binding = FragmentCallListingBinding.inflate(layoutInflater)
            return binding.root
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //observer()
        binding.callHistory.setOnClickListener{
            findNavController().navigate(R.id.action_callListingFragment2_to_callHistory)
        }
        binding.makeCall.setOnClickListener{
            findNavController().navigate(R.id.action_callListingFragment2_to_callDetailFragment)
        }

        binding.appointment.setOnClickListener{
            findNavController().navigate(R.id.action_callListingFragment2_to_appointmentListingFragment)
        }
        binding.account.setOnClickListener{
            findNavController().navigate(R.id.action_callListingFragment2_to_accountFragment2)
        }
    }

    private fun observer(){
        viewModel.appointment.observe(viewLifecycleOwner){ state ->
            when(state){
                is UiState.Loading -> {
                    binding.progressBar.show()
                }
                is UiState.Failure ->{
                    binding.progressBar.hide()
                    toast(state.error)
                }
                is UiState.Success ->{
                    binding.progressBar.hide()
                    //adapter.updateList(state.data.toMutableList())

                }
            }
        }
    }
}