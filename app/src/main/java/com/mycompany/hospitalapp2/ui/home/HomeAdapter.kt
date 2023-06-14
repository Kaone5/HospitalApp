package com.mycompany.hospitalapp2.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mycompany.hospitalapp.ui.appointment.AppointmentListingFragment
import com.mycompany.hospitalapp.ui.call.CallListingFragment
import com.mycompany.hospitalapp2.util.HomeTabs

class HomeAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = HomeTabs.values().size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            HomeTabs.APPOINTMENTS.index -> AppointmentListingFragment.newInstance(HomeTabs.APPOINTMENTS.name)
            //HomeTabs.CALLS.index -> CallListingFragment.newInstance(HomeTabs.CALLS.name)
            else -> throw IllegalStateException("Фрагмент не найден")
        }
    }
}