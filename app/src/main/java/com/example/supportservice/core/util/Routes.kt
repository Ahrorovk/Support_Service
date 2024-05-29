package com.example.supportservice.core.util

sealed class Routes(val route: String) {
    object RegistrationScreen : Routes("RegistrationScreen")
    object SettingsScreen : Routes("SettingsScreen")
    object AuthorizationScreen : Routes("AuthorizationScreen")
    object MyApplicationScreen : Routes("MyApplicationScreen")
    object MainScreen : Routes("MainScreen")
    object TestScreen : Routes("TestScreen")
    object DetailScreen : Routes("DetailScreen/{${Constants.DETAIL_ID_ARG}}")
    object DetailTestScreen :
        Routes("DetailTestScreen/{${Constants.DETAIL_TEST_ID_ARG}}/{${Constants.DETAIL_TEST_TYPE_ARG}}")

    object AgeScreen : Routes("AgeScreen")
    object StartScreen : Routes("StartScreen")
}

sealed class BottomSheet(val route: String) {
    object CallBottomSheet : BottomSheet("CallBottomSheet")
}