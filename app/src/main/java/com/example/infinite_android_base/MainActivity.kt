package com.example.infinite_android_base

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VIDEO
import android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.infinite_android_base.database.Item
import com.example.infinite_android_base.ui.theme.Infinite_android_baseTheme
import com.example.infinite_android_base.viewmodel.ItemViewModel
import com.example.infinite_android_base.viewmodel.ItemViewModelFactory
import com.example.infinite_android_base.viewmodel.PreferencesViewModel
import com.example.infinite_android_base.viewmodel.UserViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { _ ->
            // 处理权限请求结果
        }
    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            permissionLauncher.launch(
                arrayOf(READ_MEDIA_IMAGES,
                    READ_MEDIA_VIDEO,
                    READ_MEDIA_VISUAL_USER_SELECTED)
            )
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(arrayOf(READ_MEDIA_IMAGES, READ_MEDIA_VIDEO))
        } else {
            permissionLauncher.launch(arrayOf(READ_EXTERNAL_STORAGE))
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val userViewModel:UserViewModel by viewModels()
        lifecycleScope.launch {
            userViewModel.userViewMode.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).collect{
                println("jasper userViewModel collect $it")
            }
        }
//        requestPermissions()
        setContent {
            Infinite_android_baseTheme {
                Scaffold(modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)){
                        Greeting()
                        RoomTest()
                        RequestPermissionUsingAccompanist()
                    }
                }
            }
        }
    }
}

@Composable
fun RoomTest() {
    println("jasper RoomTest refresh")
    val userName = remember { mutableStateOf("") }
    val userPassword = remember { mutableStateOf("") }
    val itemViewModel:ItemViewModel = viewModel(factory = ItemViewModelFactory((LocalContext.current.applicationContext as CustomApplication).database.itemDao()))
    val itemList = remember { SnapshotStateList<Item>() }
    LaunchedEffect(Unit) {
        itemViewModel.queryAllItem().collect{
            itemList.clear()
            itemList.addAll(it)
        }
    }
    Column {
        LazyColumn {
            items(itemList.size){
                Text(text = "${itemList[it].id} ${itemList[it].name} ${itemList[it].password}")
            }
        }
        Row (){
            OutlinedTextField(modifier = Modifier.width(width = (LocalConfiguration.current.screenWidthDp/2).dp),value = userName.value, label = { Text(text = "name")} , onValueChange = {
                userName.value=it
            })
            OutlinedTextField(modifier = Modifier.width(width = (LocalConfiguration.current.screenWidthDp/2).dp),value = userPassword.value, label = { Text(text = "password")} , onValueChange = {
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
                itemViewModel.deleteAllItem()
            }) {
                Text(text = "删除所有Room")
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    println("jasper Name Greeting")
    val userViewModel:UserViewModel = viewModel()
    val userModel by userViewModel.userViewMode.collectAsState()

    val preferencesViewModel:PreferencesViewModel = viewModel(factory = PreferencesViewModel.Factory)
    val preferenceModel by preferencesViewModel.likeYou.collectAsState()
    Row(horizontalArrangement = Arrangement.SpaceEvenly){
        Text(
            text = "Hello ${userModel.name}!",
            modifier = modifier.clickable {
                userViewModel.updateName("JJJJJ")
            }
        )
        Text(
            text = "like you ${preferenceModel}!",
            modifier = modifier.clickable {
                preferencesViewModel.setLikeYou(!preferenceModel)
            }
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun RequestPermissionUsingAccompanist(){
    println("RequestPermissionUsingAccompanist refresh")
    var permissions = mutableListOf(READ_EXTERNAL_STORAGE)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
        permissions = mutableListOf(READ_MEDIA_VISUAL_USER_SELECTED)
    }else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU){
        permissions = mutableListOf(READ_MEDIA_IMAGES, READ_MEDIA_VIDEO)
    }
    val mContext = LocalContext.current
    val permissionState = rememberMultiplePermissionsState(permissions = permissions)
    LaunchedEffect(Unit) {
        permissionState.launchMultiplePermissionRequest()
    }
    if(permissionState.allPermissionsGranted){
        Text("Permission Granted.")
    }else {
        Column {
            if (permissionState.shouldShowRationale) {
                Text("The camera is important for this app. Please grant the permission.")
                Button(onClick = {
                    permissionState.launchMultiplePermissionRequest()
                }) {
                    Text("Request permission again")
                }
            }else{
                Text("Camera permission required for this feature to be available. " +
                        "Please grant the permission")
                Button(onClick = {
                    val intent = Intent()
                    intent.action =  Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    intent.data = Uri.fromParts("package", "com.example.infinite_android_base", null)
                    mContext.startActivity(intent)
                }) {
                    Text("GoSetting")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Infinite_android_baseTheme {
        Greeting()
    }
}