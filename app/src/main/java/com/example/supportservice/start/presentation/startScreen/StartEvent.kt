package com.example.supportservice.start.presentation.startScreen

sealed class StartEvent {
    object GoToMainScreen: StartEvent()
    object GoToAuthorizationScreen: StartEvent()
}