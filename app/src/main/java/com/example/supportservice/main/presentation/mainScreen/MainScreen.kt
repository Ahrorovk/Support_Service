package com.example.supportservice.main.presentation.mainScreen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    LaunchedEffect(state.selectedRoleId) {
        if (state.selectedRoleId == 0) {
            onEvent(MainEvent.GetUser)
        }
        if (state.selectedRoleId == 2) {
            onEvent(MainEvent.GetApplicationsByEmail)
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = Color.Transparent
    ) { itt ->
        LazyColumn(Modifier.padding(itt)) {
            item {
                Text(text = "MainScreen")

                state.applicationsRespState.response?.let { res ->
                    res.applications.forEach { app ->
                        Text(text = app.title)
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text(text = app.description)
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text(text = app.status)
                    }
                }

                Spacer(modifier = Modifier.padding(50.dp))
            }
        }
    }
    ProgressIndicator(isProgress = false/*state.vacanciesRespState.isLoading*/)
}


