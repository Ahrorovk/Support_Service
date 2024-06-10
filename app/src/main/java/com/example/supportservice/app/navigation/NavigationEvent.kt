package com.example.supportservice.app.navigation

sealed class NavigationEvent {
    object Clear: NavigationEvent()
}