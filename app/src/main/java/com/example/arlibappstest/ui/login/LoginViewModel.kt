package com.example.arlibappstest.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arlibappstest.data.model.User
import com.example.arlibappstest.domain.repository.UserRepository
import com.example.arlibappstest.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository, private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    val username = MutableStateFlow("")
    val password = MutableStateFlow("")
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()
    private val _currentUser = MutableSharedFlow<User>()
    val currentUser = _currentUser.asSharedFlow()
    fun login() {
        _uiState.update {
            it.copy(
                isInvalidPassword = password.value.isEmpty(),
                isInvalidUser = username.value.isEmpty()
            )
        }
        if (username.value.isEmpty() || password.value.isEmpty()) {
            return
        } else {
            viewModelScope.launch(dispatcher) {
                userRepository.login(username.value, password.value)
                    .onStart { emit(Resource.Loading(true)) }
                    .onCompletion { emit(Resource.Loading(false)) }
                    .collect { resource ->
                        when (resource) {
                            is Resource.Error -> _uiState.update { it.copy(userMessage = resource.message) }
                            is Resource.Loading -> _uiState.update { it.copy(isLoading = resource.isLoading) }
                            is Resource.Success -> {
                                _currentUser.emit(resource.data ?: return@collect)
                            }
                        }
                    }
            }
        }
    }

    fun messageHandled() {
        _uiState.update { it.copy(userMessage = null) }
    }

    fun clearData() {
        username.value = ""
        password.value = ""
    }

}

data class LoginUiState(
    val isLoading: Boolean = false,
    val isInvalidUser: Boolean = false,
    val isInvalidPassword: Boolean = false,
    val userMessage: Int? = null
)