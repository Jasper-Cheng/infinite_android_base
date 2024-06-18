package com.example.infinite_android_base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.infinite_android_base.CustomApplication
import com.example.infinite_android_base.database.UserPreferences
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PreferencesViewModel(private val userPreferences: UserPreferences) : ViewModel() {
    val likeYou= userPreferences.isLinearLayout.stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(5_000), initialValue = false)
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CustomApplication)
                PreferencesViewModel(application.userPreferences)
            }
        }
    }

    fun setLikeYou(isLinearLayout: Boolean) {
        viewModelScope.launch {
            userPreferences.saveLayoutPreference(isLinearLayout)
        }
    }
}