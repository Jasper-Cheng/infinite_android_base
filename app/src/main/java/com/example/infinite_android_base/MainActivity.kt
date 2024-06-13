package com.example.infinite_android_base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.infinite_android_base.ui.theme.Infinite_android_baseTheme
import com.example.infinite_android_base.viewmodel.UserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Infinite_android_baseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column {
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                        )
                        Greeting2(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    println("Name Greeting")
    val userViewModel:UserViewModel = viewModel()
    val model by userViewModel.userViewMode.collectAsState()
    Text(
        text = "Hello ${userViewModel.userViewMode.value.name}!",
        modifier = modifier.clickable {
            println("Name Text clickable")
            userViewModel.updateName("JJJJJ")
        }
    )
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    println("Password Greeting2")
    val userViewModel:UserViewModel = viewModel()
    val model by userViewModel.userViewMode.collectAsState()
    Text(
        text = "Hello ${userViewModel.userViewMode.value.password}!",
        modifier = modifier.clickable {
            println("Password Text clickable")
            userViewModel.updatePassword("AAAAAA")
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