package com.example.arlibappstest.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arlibappstest.ArlibApp
import com.example.arlibappstest.domain.Drug
import com.example.arlibappstest.domain.repository.MedicinesRepository
import com.example.arlibappstest.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val medicinesRepository: MedicinesRepository
) : ViewModel() {

    val greetingMessage = MutableStateFlow("")
    val isLoading = MutableStateFlow(false)
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Empty)
    val uiState = _uiState.asStateFlow()

    init {
        setGreetingMessage()
        loadMedicines()
    }

    private fun setGreetingMessage() {
        //Get the time of day
        val greeting = when (getDayHour()) {
            in 12..16 -> {
                "Good Afternoon"
            }
            in 17..20 -> {
                "Good Evening"
            }
            in 21..23 -> {
                "Good Night"
            }
            else -> {
                "Good Morning"
            }
        }
        viewModelScope.launch {
            greetingMessage.emit("$greeting ${ArlibApp.currentUser?.username}")
        }
    }

    private fun getDayHour(): Int {
        val date = Date()
        val cal: Calendar = Calendar.getInstance()
        cal.time = date
        return cal.get(Calendar.HOUR_OF_DAY)
    }

    private fun loadMedicines() {
        viewModelScope.launch(Dispatchers.IO) {
            medicinesRepository.getMedicines().collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _uiState.update { HomeUiState.ErrorState(messageId = resource.message) }
                    }
                    is Resource.Loading -> isLoading.emit(resource.isLoading)
                    is Resource.Success -> {
                        Log.d("HomeViewModel_TAG", "loadMedicines: ${resource.data}")
                        _uiState.update { HomeUiState.DataList(resource.data ?: emptyList()) }
                    }
                }
            }
        }
    }


}

sealed interface HomeUiState {
    data class ErrorState(val exceptionMessage: String? = null, val messageId: Int? = null) :
        HomeUiState

    data class DataList(val drugs: List<Drug>) : HomeUiState
    object Empty : HomeUiState
}