package com.example.smartfit.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.smartfit.AppContainer
import com.example.smartfit.ui.screens.*

@Composable
fun MainScaffold(
    navController: NavHostController,
    appContainer: AppContainer,
    startDestination: String // ✅ add this line
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    // Define which routes should have the bottom bar
    val showBottomBar = currentRoute in listOf(
        Screen.Home.route,
        Screen.Logs.route,
        Screen.Tips.route,
        Screen.Profile.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(navController = navController)
            }
        }
    ) { innerPadding ->
        // ✅ Pass startDestination into NavGraphContent
        NavGraphContent(
            navController = navController,
            appContainer = appContainer,
            innerPadding = innerPadding,
            startDestination = startDestination
        )
    }
}
