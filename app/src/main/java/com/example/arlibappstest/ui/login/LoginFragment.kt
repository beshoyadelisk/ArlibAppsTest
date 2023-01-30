package com.example.arlibappstest.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.arlibappstest.ArlibApp
import com.example.arlibappstest.R
import com.example.arlibappstest.databinding.FragmentLoginBinding
import com.example.arlibappstest.util.FragmentExt.showToast
import com.freelapp.flowlifecycleobserver.collectWhileResumed
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by viewModels()

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.uiState.collectWhileResumed(viewLifecycleOwner, ::handleUiState)
        viewModel.currentUser.collectWhileResumed(viewLifecycleOwner) {
            ArlibApp.currentUser = it
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
        }
    }

    private fun handleUiState(uiState: LoginUiState) {
        binding.ilUser.isErrorEnabled = uiState.isInvalidUser
        binding.ilPassword.isErrorEnabled = uiState.isInvalidPassword
        binding.progressBar.isVisible = uiState.isLoading

        if (uiState.isInvalidUser) {
            binding.ilUser.error = getString(R.string.required)
        } else {
            binding.ilUser.error = null
        }
        if (uiState.isInvalidPassword) {
            binding.ilPassword.error = getString(R.string.required)
        } else {
            binding.ilPassword.error = null
        }
        if (uiState.userMessage != null) {
            showToast(uiState.userMessage)
            viewModel.messageHandled()
        }
    }
}