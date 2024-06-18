package com.example.infinite_android_base.viewmodel

import androidx.lifecycle.ViewModel
import com.example.infinite_android_base.model.UserModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UserViewModel:ViewModel(){
    private val _userViewMode = MutableStateFlow(UserModel("name","password"))
    val userViewMode: StateFlow<UserModel> = _userViewMode.asStateFlow()


    fun updateName(name:String){
        _userViewMode.update {
            it.copy(name=name)
        }
    }

    fun updatePassword(password:String){
        _userViewMode.update {
            it.copy(password = password)
        }
    }

}