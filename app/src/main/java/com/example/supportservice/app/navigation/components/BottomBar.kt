package com.example.supportservice.app.navigation.components

import com.example.supportservice.core.util.Routes
import com.example.supportservice.core.util.models.BottomNavDestination
import androidx.compose.material.BottomNavigation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.navigation.NavController

@Composable
fun StandTogetherBottomBar(navController: NavController) {
    BottomNavigation(
        backgroundColor = White.copy(alpha = 0.95F),
        contentColor = MaterialTheme.colorScheme.onBackground
    ) {
        bottomNavDestinations.forEach { navItem ->
            BottomNavItem(navController = navController, item = navItem)
        }

    }
}

val bottomNavDestinations = listOf(
    BottomNavDestination(
        label = "Тесты",
        destinationRoute = Routes.TestScreen.route,
        icon = Icons.Default.DateRange
    ),
    BottomNavDestination(
        label = "Главная",
        destinationRoute = Routes.MainScreen.route,
        icon = Icons.Default.Home
    ),
    BottomNavDestination(
        label = "Настройки",
        destinationRoute = Routes.SettingsScreen.route,
        icon = Icons.Default.Settings
    )
)