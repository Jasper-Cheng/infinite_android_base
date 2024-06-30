package com.example.infinite_android_base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.infinite_android_base.CustomApplication
import com.example.infinite_android_base.configs.KeyConfig
import com.example.infinite_android_base.database.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PreferencesViewModel(private val userPreferences: UserPreferences) : ViewModel() {
    lateinit var likeYou:Flow<Boolean>
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CustomApplication)
                PreferencesViewModel(application.userPreferences)
            }
        }
    }

    init {
        viewModelScope.launch {
            likeYou= userPreferences.getBoolean(KeyConfig.IS_LIKE_YOU)
        }
    }

    fun setLikeYou(isLikeYou: Boolean) {
        viewModelScope.launch {
            userPreferences.put(KeyConfig.IS_LIKE_YOU,isLikeYou)
        }
    }
}