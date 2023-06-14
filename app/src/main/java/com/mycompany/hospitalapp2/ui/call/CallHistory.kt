package com.mycompany.hospitalapp2.ui.call

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mycompany.hospitalapp.ui.call.CallViewModel
import com.mycompany.hospitalapp2.R
import com.mycompany.hospitalapp2.databinding.FragmentAppointmentHistoryBinding
import com.mycompany.hospitalapp2.databinding.FragmentCallHistoryBinding
import com.mycompany.hospitalapp2.ui.appointment.AppointmentHistoryAdapter
import com.mycompany.hospitalapp2.ui.appointment.AppointmentViewModel
import com.mycompany.hospitalapp2.ui.auth.AuthViewModel
import com.mycompany.hospitalapp2.util.UiState
import com.mycompany.hospitalapp2.util.hide
import com.mycompany.hospitalapp2.util.show
import com.mycompany.hospitalapp2.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CallHistory : Fragment() {

    val TAG: String = "CallHistory"
    lateinit var binding: FragmentCallHistoryBinding
    val viewModel: CallViewModel by viewModels()
    val authViewModel: AuthViewModel by viewModels()
    val adapter by lazy {
        CallHistoryAdapter(
            onItemClicked = { pos, item ->
                findNavController().navigate(R.id.action_callHistory_to_callHistoryDetail,Bundle().apply {
                    putParcelable("call",item)
                })
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (this::binding.isInitialized){
            return binding.root
        }else {
            binding = FragmentCallHistoryBinding.inflate(layoutInflater)
            return binding.root
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        binding.callListing.layoutManager = staggeredGridLayoutManager
        binding.callListing.adapter = adapter

        viewModel.getCalls()
        binding.back.setOnClickListener {
            findNavController().navigate(R.id.action_callHistory_to_callListingFragment2)
        }
    }
    private fun observer(){
        viewModel.call.observe(viewLifecycleOwner){ state ->
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
                    adapter.updateList(state.data.toMutableList())
                }
            }
        }
    }


}