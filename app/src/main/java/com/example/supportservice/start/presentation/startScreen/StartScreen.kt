package com.example.supportservice.start.presentation.startScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

@Composable
fun StartScreen(
    state: StartState,
    onEvent: (StartEvent) -> Unit
) {
    LaunchedEffect(state.token) {
        delay(1000)
        if (state.token.isEmpty()) {
            onEvent(StartEvent.GoToAuthorizationScreen)
        } else {
            onEvent(StartEvent.GoToMainScreen)
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}