package com.example.supportservice.app.navigation.graph

import android.util.Log
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.supportservice.auth.presentation.authorizationScreen.AuthorizationEvent
import com.example.supportservice.auth.presentation.authorizationScreen.AuthorizationScreen
import com.example.supportservice.auth.presentation.authorizationScreen.AuthorizationViewModel
import com.example.supportservice.auth.presentation.registratrionScreen.RegistrationEvent
import com.example.supportservice.auth.presentation.registratrionScreen.RegistrationScreen
import com.example.supportservice.auth.presentation.registratrionScreen.RegistrationViewModel
import com.example.supportservice.core.util.BottomSheetApplyOneTimeEvent
import com.example.supportservice.core.util.BottomSheets
import com.example.supportservice.core.util.Constants
import com.example.supportservice.core.util.Graph
import com.example.supportservice.core.util.Routes
import com.example.supportservice.main.presentation.applicationScreen.BottomSheetApplication
import com.example.supportservice.main.presentation.applicationScreen.BottomSheetApplicationEvent
import com.example.supportservice.main.presentation.applicationScreen.BottomSheetApplicationViewModel
import com.example.supportservice.main.presentation.detailScreen.DetailEvent
import com.example.supportservice.main.presentation.detailScreen.DetailScreen
import com.example.supportservice.main.presentation.detailScreen.DetailViewModel
import com.example.supportservice.main.presentation.mainScreen.MainEvent
import com.example.supportservice.main.presentation.mainScreen.MainScreen
import com.example.supportservice.main.presentation.mainScreen.MainViewModel
import com.example.supportservice.start.presentation.startScreen.StartEvent
import com.example.supportservice.start.presentation.startScreen.StartScreen
import com.example.supportservice.start.presentation.startScreen.StartViewModel
import com.example.supportservice.user.presentation.userScreen.UserEvent
import com.example.supportservice.user.presentation.userScreen.UserScreen
import com.example.supportservice.user.presentation.userScreen.UserViewModel
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import kotlinx.coroutines.delay

@ExperimentalMaterialNavigationApi
fun NavGraphBuilder.CallNavGraph(
    navController: NavController,
    topBarTitle: MutableState<String>,
    scaffoldState: ScaffoldState
) {
    navigation(
        route = Graph.MainGraph.route,
        startDestination = Routes.StartScreen.route
    ) {
        composable(Routes.StartScreen.route) {
            val viewModel = hiltViewModel<StartViewModel>()
            val state = viewModel.state.collectAsState()
            StartScreen(state = state.value) { event ->
                when (event) {
                    StartEvent.GoToMainScreen -> {
                        navController.navigate(Routes.MainScreen.route) {
                            popUpTo(Routes.StartScreen.route) {
                                inclusive = true
                            }
                        }
                    }

                    StartEvent.GoToAuthorizationScreen -> {
                        navController.navigate(Routes.AuthorizationScreen.route) {
                            popUpTo(Routes.StartScreen.route) {
                                inclusive = true
                            }
                        }
                    }

                    else -> {
                        viewModel.onEvent(event)
                    }
                }
            }
        }

        bottomSheet(
            BottomSheets.ApplicationSheet.route, arguments = listOf(
                navArgument(Constants.EMAIL_ARG) {
                    type = NavType.StringType
                }, navArgument(Constants.PHONE_NUMBER_ARG) {
                    type = NavType.StringType
                })
        ) { backstackEntry ->
            val email = backstackEntry.arguments?.getString(Constants.EMAIL_ARG) ?: ""
            val phoneNumber = backstackEntry.arguments?.getString(Constants.PHONE_NUMBER_ARG) ?: ""
            val viewModel = hiltViewModel<BottomSheetApplicationViewModel>()
            val state = viewModel.state.collectAsState()
            LaunchedEffect(key1 = true) {
                viewModel.onEvent(BottomSheetApplicationEvent.OnEmailChange(email))
                viewModel.onEvent(BottomSheetApplicationEvent.OnPhoneNumberChange(phoneNumber))
            }
            LaunchedEffect(key1 = false, block = {
                viewModel.flow.collect() { event ->
                    when (event) {
                        is BottomSheetApplyOneTimeEvent.CloseBottomSheet -> {
                            if (state.value.isAdded)
                                navController.popBackStack()
                        }
                    }
                }
            })
            BottomSheetApplication(
                state = state.value,
                onEvent = { event ->
                    when (event) {
                        BottomSheetApplicationEvent.GoToMain -> {
                            navController.popBackStack()
                        }

                        else -> viewModel.onEvent(event)
                    }
                }
            )
        }

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

                        RegistrationEvent.GoToAuthScreen -> {
                            navController.navigate(Routes.AuthorizationScreen.route) {
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
        composable(
            Routes.DetailScreen.route, arguments = listOf(
                navArgument(Constants.DETAIL_ID_ARG) {
                    type = NavType.IntType
                })
        ) { backstackEntry ->
            val id = backstackEntry.arguments?.getInt(Constants.DETAIL_ID_ARG) ?: 0
            val viewModel = hiltViewModel<DetailViewModel>()
            val state = viewModel.state.collectAsState()
            LaunchedEffect(key1 = true) {
                Log.e("TAG", "TAG_ID->$id")
                viewModel.onEvent(DetailEvent.OnIdChange(id))
                viewModel.onEvent(DetailEvent.GetAllStatuses)
            }
            DetailScreen(state = state.value) { event ->
                when (event) {
                    DetailEvent.GoToMain -> {
                        navController.popBackStack()
                    }

                    else -> viewModel.onEvent(event)
                }
            }
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

                        MainEvent.GoToDetailApplication -> {
                            Log.e("TAG", "ID->->->${state.value.appId}")
                            navController.navigate(
                                Routes.DetailScreen.route.replace(
                                    "{${Constants.DETAIL_ID_ARG}}",
                                    "${state.value.appId}",
                                )
                            )
                        }

                        is MainEvent.GoToApplication -> {
                            navController.navigate(
                                BottomSheets.ApplicationSheet.route.replace(
                                    "{${Constants.EMAIL_ARG}}/{${Constants.PHONE_NUMBER_ARG}}",
                                    "${state.value.email}/${state.value.phoneNumber}",
                                )
                            )
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

        composable(Routes.UserScreen.route) {
            val viewModel = hiltViewModel<UserViewModel>()
            val state = viewModel.state.collectAsState()

            LaunchedEffect(true) {
                delay(300)
                viewModel.onEvent(UserEvent.GetUser)
            }

            UserScreen(state.value) { event ->
                when (event) {
                    else -> viewModel.onEvent(event)
                }
            }
        }
    }
}