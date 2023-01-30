package com.example.arlibappstest.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.arlibappstest.R
import com.example.arlibappstest.databinding.FragmentHomeBinding
import com.example.arlibappstest.domain.Drug
import com.example.arlibappstest.util.FragmentExt.showToast
import com.freelapp.flowlifecycleobserver.collectWhileResumed
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private val adapter = DrugAdapter(::onDrugClicked)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvItems.adapter = adapter
        binding.btnRefresh.setOnClickListener { viewModel.loadMedicines(true) }
        viewModel.uiState.collectWhileResumed(viewLifecycleOwner, ::handleUiState)
    }

    private fun handleUiState(homeUiState: HomeUiState) {
        when (homeUiState) {
            HomeUiState.Empty -> Unit
            is HomeUiState.DataList -> {
                adapter.submitList(homeUiState.drugs)
            }
            is HomeUiState.ErrorState -> {
                showToast(homeUiState.messageId ?: R.string.failed_to_get_data)
            }
        }
    }

    private fun onDrugClicked(drug: Drug) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(drug))
    }
}