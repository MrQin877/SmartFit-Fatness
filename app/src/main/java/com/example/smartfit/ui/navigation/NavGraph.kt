// app/src/main/java/com/example/smartfit/ui/navigation/NavGraphContent.kt
package com.example.smartfit.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.smartfit.AppContainer
import com.example.smartfit.ui.screens.*

@OptIn(ExperimentalAnimationApi::class)
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

        composable(Screen.Home.route) {
            HomeScreen(navController, appContainer, contentPadding = innerPadding)
        }
        composable(Screen.Logs.route) {
            LogsScreen(navController, appContainer, contentPadding = innerPadding)
        }
        composable(
            route = Screen.LogDetail.route,
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: 0L
            LogDetailScreen(navController, appContainer, id)
        }

        // --- Animated sheet route ---
        composable(
            route = Screen.AddLog.route,
            enterTransition = {
                // slide IN from bottom over current screen
                slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(200))
            },
            exitTransition = {
                // when navigating forward away from AddLog (rare) — fade
                fadeOut(tween(150))
            },
            popEnterTransition = {
                // returning back to AddLog — simple fade
                fadeIn(tween(150))
            },
            popExitTransition = {
                // when popping AddLog (close/back) — slide OUT to bottom
                slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(200))
            }
        ) {
            AddLogScreen(navController, appContainer)
        }

        composable(Screen.Tips.route) { TipsScreen(navController, appContainer) }
        composable(Screen.Profile.route) { ProfileScreen(navController, appContainer) }
    }
}
