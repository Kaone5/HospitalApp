package com.mycompany.hospitalapp2.ui.call

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mycompany.hospitalapp.ui.call.CallViewModel
import com.mycompany.hospitalapp2.R
import com.mycompany.hospitalapp2.databinding.FragmentCallServiceListingBinding
import com.mycompany.hospitalapp2.util.UiState
import com.mycompany.hospitalapp2.util.hide
import com.mycompany.hospitalapp2.util.show
import com.mycompany.hospitalapp2.util.toast

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CallServiceListingFragment : Fragment() {
    val TAG: String = "CallServiceListingFragment"
    lateinit var binding: FragmentCallServiceListingBinding
    val viewModel: CallViewModel by viewModels()

    val adapter by lazy {
        CallServiceListingAdapter(
            onItemClicked = { pos, item ->
                findNavController().
                navigate(
                    R.id.action_callServiceListingFragment_to_callDetailFragment,
                    Bundle().apply {
                    putParcelable("servicecall",item)
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
            binding = FragmentCallServiceListingBinding.inflate(layoutInflater)
            return binding.root
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
        binding.callServiceListing.layoutManager = staggeredGridLayoutManager
        binding.callServiceListing.adapter = adapter

        viewModel.getCallService()
        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
    }
    private fun observer(){
        viewModel.service.observe(viewLifecycleOwner){ state ->
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