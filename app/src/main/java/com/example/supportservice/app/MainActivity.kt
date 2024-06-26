package com.example.supportservice.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.supportservice.R
import com.example.supportservice.app.navigation.Navigation
import com.example.supportservice.app.navigation.NavigationViewModel
import com.example.supportservice.core.data.local.DataStoreManager
import com.example.supportservice.core.presentation.ui.theme.SupportServiceTheme
import com.example.supportservice.core.util.Constants
import com.example.supportservice.service.SupportFirebaseMessagingService
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialNavigationApi
@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val supportFirebaseMessagingService = SupportFirebaseMessagingService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataStoreManager = DataStoreManager(applicationContext)
        supportFirebaseMessagingService.onCreate()

        setContent {
            LaunchedEffect(Constants.FCM_TOKEN) {
                if (Constants.FCM_TOKEN.isNotEmpty()) {

                    dataStoreManager.updateFcmTokenKey(Constants.FCM_TOKEN)
                }
            }
            SupportServiceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel = hiltViewModel<NavigationViewModel>()
                    val state = viewModel.state.collectAsState()
                    Navigation(state.value) { event ->
                        viewModel.onEvent(event)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String = "Android", modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Row {
            Column {
                Image(
                    painter = painterResource(id = R.drawable.app_icon),
                    contentDescription = "App Icon",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(horizontal = 15.dp)
                )
                Text(
                    text = "Hello $name!"
                )
            }
            Text(text = "Hi $name")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SupportServiceTheme {
        Greeting("")
    }
}