package com.example.supportservice.app.navigation.graph

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.supportservice.main.presentation.mainScreen.MainEvent
import com.example.supportservice.main.presentation.mainScreen.MainScreen
import com.ahrorovkspace.afkorhackathon.presentation.mainScreen.MainViewModel
import com.example.supportservice.auth.presentation.authorizationScreen.AuthorizationEvent
import com.example.supportservice.auth.presentation.authorizationScreen.AuthorizationScreen
import com.example.supportservice.auth.presentation.authorizationScreen.AuthorizationViewModel
import com.example.supportservice.auth.presentation.registratrionScreen.RegistrationEvent
import com.example.supportservice.auth.presentation.registratrionScreen.RegistrationScreen
import com.example.supportservice.auth.presentation.registratrionScreen.RegistrationViewModel
import com.example.supportservice.core.util.Graph
import com.example.supportservice.core.util.Routes
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi

@ExperimentalMaterialNavigationApi
fun NavGraphBuilder.CallNavGraph(
    navController: NavController,
    topBarTitle: MutableState<String>,
    scaffoldState: ScaffoldState
) {
    navigation(
        route = Graph.MainGraph.route,
        startDestination = Routes.AuthorizationScreen.route
    ) {
        composable(Routes.AuthorizationScreen.route) {
            val viewModel = hiltViewModel<AuthorizationViewModel>()
            val state = viewModel.state.collectAsState()
            AuthorizationScreen(
                state = state.value,
                onEvent = { event ->
                    when (event) {
                        AuthorizationEvent.GoToSignUp -> {
                            navController.navigate(Routes.RegistrationScreen.route)
                        }

                        AuthorizationEvent.GoToMainScreen -> {
                            navController.navigate(Routes.MainScreen.route) {
                                popUpTo(Routes.AuthorizationScreen.route) {
                                    inclusive = true
                                }
                            }
                        }

                        else -> {
                            viewModel.onEvent(event)
                        }
                    }
                }
            )
        }

        composable(
            Routes.RegistrationScreen.route
        ) {
            val viewModel = hiltViewModel<RegistrationViewModel>()
            val state = viewModel.state.collectAsState()

            RegistrationScreen(
                state = state.value,
                onEvent = { event ->
                    when (event) {
                        RegistrationEvent.GoToSignUp -> {
                            navController.navigate(Routes.AuthorizationScreen.route) {
                                popUpTo(Routes.RegistrationScreen.route) {
                                    inclusive = true
                                }
                            }
                        }

                        RegistrationEvent.GoToMainScreen -> {
                            navController.navigate(Routes.MainScreen.route) {
                                popUpTo(Routes.RegistrationScreen.route) {
                                    inclusive = true
                                }
                            }
                        }

                        else -> viewModel.onEvent(event)
                    }
                }
            )
        }
        composable(Routes.MainScreen.route) {
            val viewModel = hiltViewModel<MainViewModel>()
            val state = viewModel.state.collectAsState()
            viewModel.onEvent(MainEvent.OnSearchProjectChange(""))
            MainScreen(
                state = state.value,
                scaffoldState = scaffoldState,
                onEvent = { event ->
                    when (event) {
                        MainEvent.GoToAuthorization -> {
                            navController.navigate(Routes.AuthorizationScreen.route)
                        }

                        MainEvent.Logout -> {
                            navController.navigate(Routes.AuthorizationScreen.route) {
                                popUpTo(Routes.MainScreen.route) {
                                    inclusive = true
                                }
                            }
                        }

                        MainEvent.GoToMyApplication -> {
                            navController.navigate(Routes.MyApplicationScreen.route)
                        }

                        is MainEvent.GoToApplication -> {
//                        navController.navigate(
//                            BottomSheets.ApplicationSheet.route.replace(
//                                "{${Constants.PROJECT_ID_ARG}}",
//                                "${event.id}",
//                            )
//                        )
                        }

                        MainEvent.GoToRegistration -> {
                            navController.navigate(Routes.RegistrationScreen.route)
                        }

                        MainEvent.GoToSettings -> {
                            navController.navigate(Routes.SettingsScreen.route)
                        }

                        else -> {
                            viewModel.onEvent(event)
                        }
                    }
                }
            )
        }
    }
}