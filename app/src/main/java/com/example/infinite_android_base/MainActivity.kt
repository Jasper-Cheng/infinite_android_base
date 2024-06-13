package com.example.infinite_android_base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.infinite_android_base.model.UserModel
import com.example.infinite_android_base.ui.theme.Infinite_android_baseTheme
import com.example.infinite_android_base.viewmodel.UserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        val userViewModel:UserViewModel by viewModels()
//        userViewModel.userViewMode.observe(this, Observer {
//            println("${userViewModel.userViewMode.value}")
//        })
        setContent {
            Infinite_android_baseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val userViewModel:UserViewModel = viewModel()
//    userViewModel.userViewMode.observe(LocalLifecycleOwner.current, Observer {
//        println("${userViewModel.userViewMode.value}")
//    })
    Text(
        text = "Hello $name! ${userViewModel.userViewMode.value.name}",
        modifier = modifier.clickable {
            println("modifier.clickable ${userViewModel.userViewMode.value}")
            userViewModel.userViewMode.value= UserModel("Joyce")
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Infinite_android_baseTheme {
        Greeting("Android")
    }
}