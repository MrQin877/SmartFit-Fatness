// app/src/main/java/com/example/smartfit/ui/navigation/NavGraphContent.kt
package com.example.smartfit.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.smartfit.di.AppGraph
import com.example.smartfit.ui.auth.LoginScreen
import com.example.smartfit.ui.auth.OnboardingScreen
import com.example.smartfit.ui.auth.SignUpScreen
import com.example.smartfit.ui.home.HomeScreen
import com.example.smartfit.ui.logs.AddLogScreen
import com.example.smartfit.ui.logs.LogDetailScreen
import com.example.smartfit.ui.logs.LogsScreen
import com.example.smartfit.ui.profile.ProfileScreen
import com.example.smartfit.ui.tips.TipsScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraphContent(
    navController: NavHostController,
    graph: AppGraph,
    innerPadding: PaddingValues,
    startDestination: Dest,
    isDark: Boolean

) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // ----- Auth / onboarding
        composable<Dest.Onboarding> {
            OnboardingScreen(navController)
        }
        composable<Dest.Login> {
            LoginScreen(navController,isDark = isDark)
        }
        composable<Dest.SignUp> {
            SignUpScreen(navController, isDark = isDark)
        }

        // ----- Top-level tabs (remember to pass innerPadding)
        composable<Dest.Home> {
            HomeScreen(navController,contentPadding = innerPadding)
        }
        composable<Dest.Logs> {
            LogsScreen(navController,contentPadding = innerPadding)
        }
        composable<Dest.Tips> {
            TipsScreen(navController, graph)
        }
        composable<Dest.Profile> {
            ProfileScreen(navController,)
        }

        // ----- Detail with type-safe argument
        composable<Dest.LogDetail> { backStackEntry ->
            val args = backStackEntry.toRoute<Dest.LogDetail>()
            LogDetailScreen(navController, isDark = isDark)
        }

        // ----- Bottom-sheet style AddLog with transitions
        composable<Dest.AddLog>(
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(200))
            },
            exitTransition = { fadeOut(tween(150)) },
            popEnterTransition = { fadeIn(tween(150)) },
            popExitTransition = {
                slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(200))
            }
        ) {
            AddLogScreen(navController)
        }
    }
}
