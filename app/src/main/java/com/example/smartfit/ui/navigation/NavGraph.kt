package com.example.smartfit.ui.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
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

    // Observe login state from DataStore
    val isLoggedIn by dataStore.data
        .map { it[booleanPreferencesKey("is_logged_in")] ?: false }
        .collectAsState(initial = false)

    val startDestination = if (isLoggedIn) Screen.Home.route else Screen.Login.route

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Login.route) { LoginScreen(navController, appContainer) }
        composable(Screen.SignUp.route) { SignUpScreen(navController, appContainer) }
        composable(Screen.Onboarding.route) { OnboardingScreen(navController, appContainer) }
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
