package com.mycompany.hospitalapp.ui.appointment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mycompany.hospitalapp2.ui.appointment.AppointmentViewModel
import com.mycompany.hospitalapp2.databinding.FragmentAppointmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppointmentDetailFragment: Fragment() {
    val TAG: String = "AppointmentDetailFragment"
    lateinit var binding: FragmentAppointmentDetailBinding
    val viewModel: AppointmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (this::binding.isInitialized){
            return binding.root
        }else {
            binding = FragmentAppointmentDetailBinding.inflate(layoutInflater)
            return binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}