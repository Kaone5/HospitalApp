package com.mycompany.hospitalapp.ui.appointment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mycompany.hospitalapp2.R
import com.mycompany.hospitalapp2.ui.appointment.AppointmentViewModel
import com.mycompany.hospitalapp2.databinding.FragmentAppointmentListingBinding
import com.mycompany.hospitalapp2.util.UiState
import com.mycompany.hospitalapp2.util.hide
import com.mycompany.hospitalapp2.util.show
import com.mycompany.hospitalapp2.util.toast
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"

@AndroidEntryPoint
class AppointmentListingFragment : Fragment() {

    val TAG: String = "AppointmentListingFragment"
    lateinit var binding: FragmentAppointmentListingBinding
    val viewModel: AppointmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (this::binding.isInitialized){
            return binding.root
        }else {
            binding = FragmentAppointmentListingBinding.inflate(layoutInflater)
            return binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        binding.appointmentHistory.setOnClickListener{
            findNavController().navigate(R.id.action_appointmentListingFragment_to_appointmentHistory)
        }
        binding.makeAppointment.setOnClickListener{
            findNavController().navigate(R.id.action_appointmentListingFragment_to_doctorRoleListing)
        }
        binding.call.setOnClickListener{
            findNavController().navigate(R.id.action_appointmentListingFragment_to_callListingFragment2)
        }
        binding.account.setOnClickListener{
            findNavController().navigate(R.id.action_appointmentListingFragment_to_accountFragment2)
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
    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            AppointmentListingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

}