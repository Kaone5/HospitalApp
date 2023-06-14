package com.mycompany.hospitalapp2.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mycompany.hospitalapp2.R
import com.mycompany.hospitalapp2.ui.auth.AuthViewModel
import com.mycompany.hospitalapp2.databinding.FragmentHomeBinding
import com.mycompany.hospitalapp2.util.HomeTabs
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    val TAG: String = "HomeFragment"
    lateinit var binding: FragmentHomeBinding
    val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logout.setOnClickListener {
            authViewModel.logout {
                findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
            }
        }
        binding.viewPager2.adapter = HomeAdapter(this)
        viewPager2SetupWithTabLayout(
            tabLayout = binding.tabLayout,
            viewPager2 = binding.viewPager2
        )
        binding.tabLayout.onTabSelectionListener()
    }
    private fun viewPager2SetupWithTabLayout(tabLayout: TabLayout, viewPager2: ViewPager2){
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            val view = LayoutInflater.from(requireContext()).inflate(R.layout.item_tab_layout, null)
            val textViewTitle: TextView = view.findViewById<TextView>(R.id.tabTitle)
            when (position) {
                HomeTabs.APPOINTMENTS.index -> {
                    tab.customView = view
                    textViewTitle.text = getString(R.string.appointments)
                    tab.onSelection(true)
                }
                HomeTabs.CALLS.index -> {
                    tab.customView = view
                    textViewTitle.text = getString(R.string.calls)
                    tab.onSelection(false)
                }
            }
        }.attach()
    }
}


inline fun TabLayout.onTabSelectionListener(
    crossinline onTabSelected: (TabLayout.Tab?) -> Unit = {},
    crossinline onTabUnselected: (TabLayout.Tab?) -> Unit? = {},
) {
    addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            tab?.onSelection(true)
            onTabSelected.invoke(tab)
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
            tab?.onSelection(false)
            onTabUnselected.invoke(tab)
        }

        override fun onTabReselected(tab: TabLayout.Tab?) {}
    })
}

fun TabLayout.Tab.onSelection(isSelected: Boolean = true) {
    val textViewTitle: TextView? = customView?.findViewById<TextView>(R.id.tabTitle)
    textViewTitle?.let { textView ->
        textView.setTextColor(
            ContextCompat.getColor(
                textView.context,
                if (isSelected) R.color.tab_selected else R.color.tab_unselected
            )
        )
    }
}