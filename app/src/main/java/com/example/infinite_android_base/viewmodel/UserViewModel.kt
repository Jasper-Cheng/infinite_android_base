package com.example.infinite_android_base.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.infinite_android_base.model.UserModel

class UserViewModel:ViewModel(){
    var userViewMode: MutableLiveData<UserModel> = MutableLiveData()

    init {
        val userModel=UserModel("Jasper")
        userViewMode.value=userModel
    }

}