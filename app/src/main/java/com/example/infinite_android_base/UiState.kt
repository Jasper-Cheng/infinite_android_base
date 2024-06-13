package com.example.infinite_android_base

sealed class UiState {
    data object Idle : UiState()

    data class NormalUpdate(val user: String) : UiState()
}

sealed class LoginIntent {
    data class NameChanged(val name: String) : LoginIntent()
    data object SubmitLogin : LoginIntent()
}