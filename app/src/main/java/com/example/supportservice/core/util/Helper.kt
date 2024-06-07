package com.example.supportservice.core.util

import com.example.supportservice.auth.presentation.registratrionScreen.RegistrationState
import com.example.supportservice.core.domain.states.RoleState
import com.example.supportservice.main.domain.main.models.application.Application
import com.example.supportservice.main.domain.main.models.status.Status

fun doesScreenHaveBottomBar(currentScreen: String) =
    currentScreen != Routes.RegistrationScreen.route &&
            currentScreen != Routes.AuthorizationScreen.route &&
            currentScreen != Routes.StartScreen.route


fun Int.ageToId(): Int {
    return when (this) {
        in 12..15 -> 0
        in 16..18 -> 1
        in 19..25 -> 2
        in 26..70 -> 3
        else -> 0
    }
}
fun sortOrdersByStatus(orders: List<Application>, selectedStatus: String): List<Application> {
    return if (selectedStatus != "Все") {
        orders.sortedBy { order ->
            order.status == selectedStatus
        }
    } else {
        orders
    }
}
fun doesScreenHavePopBack(currentScreen: String): Boolean {
    return currentScreen != Routes.MainScreen.route &&
            currentScreen != Routes.SettingsScreen.route &&
            currentScreen != Routes.UserScreen.route &&
            currentScreen != Routes.StartScreen.route
}

fun doesScreenHaveTopBar(currentScreen: String): Boolean {
    return currentScreen != Routes.RegistrationScreen.route &&
            currentScreen != Routes.AuthorizationScreen.route &&
            currentScreen != Routes.StartScreen.route
}

fun getScoreTitle(currentScreen: String): Boolean {
    return currentScreen == Routes.DetailTestScreen.route
}


fun getTopBarTitle(language: String, currentScreen: String): String {
    return when (currentScreen) {
        Routes.MainScreen.route -> "Главная"
        Routes.TestScreen.route -> "Тесты"
        Routes.SettingsScreen.route -> "Настройки"
        else -> ""
    }
}

fun getEnable(state: RegistrationState) = state.email.isNotEmpty() &&
        state.password.isNotEmpty() &&
        state.passwordConfirm.isNotEmpty() &&
        state.passwordConfirm == state.password &&
        state.username.isNotEmpty() &&
        state.phone.isNotEmpty()

fun getRoles() =
    listOf<RoleState>(
        RoleState(
            "Клиент", 1
        ),
        RoleState(
            "Оператор", 2
        )
    )
