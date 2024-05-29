package com.example.supportservice.main.presentation.mainScreen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.supportservice.core.presentation.components.ProgressIndicator

@Composable
fun MainScreen(
    state: MainState,
    scaffoldState: ScaffoldState,
    onEvent: (MainEvent) -> Unit
) {
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            /*DrawerContent(
                openLoginScreen = {
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                    onEvent(MainEvent.GoToAuthorization)
                },
                openRegistrationScreen = {
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                    onEvent(MainEvent.GoToRegistration)
                },
                openSettingsScreen = {
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                    onEvent(MainEvent.GoToSettings)
                },
                openDonationsLink = { /*TODO*/ },
                openGithubPage = { /*TODO*/ },
                shareApp = { /*TODO*/ },
                sendEmail = { /*TODO*/ },
                isReg = state.refreshToken.isNotEmpty(),
                logOut = {
                    onEvent(MainEvent.Clean)
                    onEvent(MainEvent.Logout)
                }
            )*/
        },
        backgroundColor = Color.Transparent
    ) { itt ->
        LazyColumn(Modifier.padding(itt)) {
            item {
                Text(text = "MainScreen")
//                state.vacanciesRespState.response?.let { item ->
//                    item.results.forEachIndexed { ind, vacancy ->
//                        Log.e("TAG","${vacancy.phone_number}")
                /*CustomProjectItem(
                    vacancy = vacancy
                ) {
                    onEvent(MainEvent.GoToApplication(it))
                }*/
                Spacer(modifier = Modifier.padding(10.dp))
//                    }
                Spacer(modifier = Modifier.padding(50.dp))
            }
        }
    }
    ProgressIndicator(isProgress = false/*state.vacanciesRespState.isLoading*/)
}


