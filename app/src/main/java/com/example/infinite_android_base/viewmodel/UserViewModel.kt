package com.example.infinite_android_base.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.infinite_android_base.model.UserModel

class UserViewModel:ViewModel(){
    var userViewMode: MutableState<UserModel> = mutableStateOf(UserModel("Jasper"))

}