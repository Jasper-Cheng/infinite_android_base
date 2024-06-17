package com.example.infinite_android_base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.infinite_android_base.database.Item
import com.example.infinite_android_base.ui.theme.Infinite_android_baseTheme
import com.example.infinite_android_base.viewmodel.ItemViewModel
import com.example.infinite_android_base.viewmodel.ItemViewModelFactory
import com.example.infinite_android_base.viewmodel.UserViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val userViewModel:UserViewModel by viewModels()
        lifecycleScope.launch {
            userViewModel.userViewMode.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).collect{
                println("userViewModel collect $it")
            }
        }
        setContent {
            Infinite_android_baseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column {
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                        )
                        RoomTest(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}

@Composable
fun RoomTest(modifier: Modifier = Modifier){
    val userName = remember { mutableStateOf("") }
    val userPassword = remember { mutableStateOf("") }
    val itemViewModel:ItemViewModel = viewModel(factory = ItemViewModelFactory((LocalContext.current.applicationContext as CustomApplication).database.itemDao()))
    var items = flow<List<Item>> {  }
    runBlocking {
        println("runBlocking")
        items=itemViewModel.queryAllItem().await()
    }

    Column {
        LazyColumn {
            items.map { item ->
                items(item.size){
                    println("jasper ${item[it]}")
                    Text(text = "${item[it].id} ${item[it].name} ${item[it].password}")
                }

            }
        }
        Row (horizontalArrangement = Arrangement.SpaceEvenly,modifier = Modifier.fillMaxWidth()){
            OutlinedTextField(value = userName.value, label = { Text(text = "name")} , onValueChange = {
                userName.value=it
            })
            OutlinedTextField(value = userPassword.value, label = { Text(text = "password")} , onValueChange = {
                userPassword.value=it
            })
        }
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()){
            OutlinedButton(onClick = {
                itemViewModel.addNewItem(userName.value,userPassword.value)
            }) {
                Text(text = "添加到Room")
            }
            OutlinedButton(onClick = {
//                itemViewModel.deleteAllItem()
                itemViewModel.deleteAllItem()
            }) {
                Text(text = "删除所有Room")
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
        text = "Hello ${model.name}!",
        modifier = modifier.clickable {
            println("Name Text clickable")
            userViewModel.updateName("JJJJJ")
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