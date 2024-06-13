package com.example.infinite_android_base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class UiViewModel:ViewModel() {

    private val loginIntent = Channel<LoginIntent>(Channel.UNLIMITED)
    private val _uiState = MutableStateFlow<UiState>(UiState.NormalUpdate("default"))
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        handleIntent()
    }

    fun sendIntent(intent: LoginIntent){
        viewModelScope.launch {
            loginIntent.send(intent)
        }
    }

    private fun handleIntent(){
        viewModelScope.launch {
            //处理不同的Intent
            loginIntent.consumeAsFlow().collect{
                println("loginIntent collect $it")
                when (it) {
                    is LoginIntent.NameChanged -> {
                        _uiState.value = UiState.NormalUpdate(it.name)
                    }
                    LoginIntent.SubmitLogin -> {
                        _uiState.value = UiState.NormalUpdate("UnKnow")
                    }
                }
            }
        }
    }

}