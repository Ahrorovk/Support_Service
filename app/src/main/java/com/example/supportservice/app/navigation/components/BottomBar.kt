package com.example.supportservice.app.navigation.components

import com.example.supportservice.core.util.Routes
import com.example.supportservice.core.util.models.BottomNavDestination
import androidx.compose.material.BottomNavigation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.navigation.NavController

@Composable
fun SupportServiceBottomBar(navController: NavController) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        bottomNavDestinations.forEach { navItem ->
            BottomNavItem(navController = navController, item = navItem)
        }

    }
}

val bottomNavDestinations = listOf(
    BottomNavDestination(
        label = "Главная",
        destinationRoute = Routes.MainScreen.route,
        icon = Icons.Default.Home
    ),
    BottomNavDestination(
        label = "Профиль",
        destinationRoute = Routes.UserScreen.route,
        icon = Icons.Default.Person
    )
)