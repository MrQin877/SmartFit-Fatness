package com.example.smartfit.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.example.smartfit.AppContainer
import com.example.smartfit.data.datastore.dataStore
import com.example.smartfit.ui.screens.*
import kotlinx.coroutines.flow.map


sealed class Screen(val route: String) {
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
    object Logs : Screen("logs")
    object LogDetail : Screen("log/{id}") {
        fun createRoute(id: Long) = "log/$id"
    }
    object AddLog : Screen("log/new")
    object Tips : Screen("tips")
    object Profile : Screen("profile")
}

@Composable
fun NavGraph(appContainer: AppContainer) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val dataStore = context.dataStore

    // Collect DataStore prefs safely
    val prefsState = dataStore.data.collectAsState(initial = null)
    val prefs = prefsState.value


    // Show loading screen until prefs are loaded
    if (prefs == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    // Once loaded, extract stored flags
    val isOnboarded = prefs[booleanPreferencesKey("is_onboarded")] ?: false
    val isLoggedIn = prefs[booleanPreferencesKey("is_logged_in")] ?: false

    val startDestination = when {
        !isOnboarded -> Screen.Onboarding.route
        !isLoggedIn -> Screen.Login.route
        else -> Screen.Home.route
    }

    // ✅ Scaffold for screens that use bottom bar
    Scaffold(
        bottomBar = {
            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            if (currentRoute in listOf(
                    Screen.Home.route,
                    Screen.Logs.route,
                    Screen.Tips.route,
                    Screen.Profile.route
                )
            ) {
                BottomNavBar(navController)
            }
        }
    ) { innerPadding ->
        NavGraphContent(
            navController = navController,
            appContainer = appContainer,
            innerPadding = innerPadding,
            startDestination = startDestination
        )
    }
}


@Composable
fun NavGraphContent(
    navController: NavHostController,
    appContainer: AppContainer,
    innerPadding: PaddingValues,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Onboarding.route) { OnboardingScreen(navController, appContainer) }
        composable(Screen.Login.route) { LoginScreen(navController, appContainer) }
        composable(Screen.SignUp.route) { SignUpScreen(navController, appContainer) }
        composable(Screen.Home.route) { HomeScreen(navController, appContainer) }
        composable(Screen.Logs.route) { LogsScreen(navController, appContainer) }
        composable(
            route = Screen.LogDetail.route,
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: 0L
            LogDetailScreen(navController, appContainer, id)
        }
        composable(Screen.AddLog.route) { AddLogScreen(navController, appContainer) }
        composable(Screen.Tips.route) { TipsScreen(navController, appContainer) }
        composable(Screen.Profile.route) { ProfileScreen(navController, appContainer) }
    }
}
