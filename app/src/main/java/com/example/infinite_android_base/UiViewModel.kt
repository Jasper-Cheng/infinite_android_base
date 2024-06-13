package com.example.infinite_android_base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UiViewModel:ViewModel() {
    private val _uiState = MutableStateFlow(UiState("Jasper"))
    val uiState:StateFlow<UiState> = _uiState.asStateFlow()

    fun updateName(){
        _uiState.update { currentStata ->
            currentStata.copy(name = "JJJJJ")
        }
    }

}