package com.example.supportservice.app.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.supportservice.app.navigation.components.SupportServiceBottomBar
import com.example.supportservice.app.navigation.graph.CallNavGraph
import com.example.supportservice.core.presentation.components.CustomIconButton
import com.example.supportservice.core.util.Graph
import com.example.supportservice.core.util.doesScreenHaveBottomBar
import com.example.supportservice.core.util.doesScreenHavePopBack
import com.example.supportservice.core.util.doesScreenHaveTopBar
import com.example.supportservice.core.util.getTopBarTitle
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator

@ExperimentalMaterialNavigationApi
@ExperimentalMaterial3Api
@Composable
fun Navigation() {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)

    val topBarTitle = remember { mutableStateOf("") }
    val scaffoldState = rememberScaffoldState()

    val currentScreen = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""
    ModalBottomSheetLayout(bottomSheetNavigator) {
        Scaffold(
            bottomBar = {
                if (doesScreenHaveBottomBar(currentScreen)) {
                    SupportServiceBottomBar(navController)
                }
            },
            topBar = {
                if (doesScreenHaveTopBar(currentScreen))
                    TopAppBar(title = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (getTopBarTitle(
                                        language = "tg",
                                        currentScreen
                                    ).isNotEmpty()
                                )
                                    getTopBarTitle(language = "tg", currentScreen)
                                else topBarTitle.value,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    },
                        navigationIcon = {
                            if (doesScreenHavePopBack(currentScreen))
                                CustomIconButton(icon = Icons.Filled.KeyboardArrowLeft) {
                                    navController.popBackStack()
                                }
                        }
                    )
            }
        ) { it ->
            NavHost(
                modifier = Modifier.padding(it),
                navController = navController,
                startDestination = Graph.MainGraph.route
            ) {
                CallNavGraph(navController, topBarTitle, scaffoldState)
            }
        }
    }
}