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
import com.example.smartfit.ui.activitystats.*

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

        // ✅ 新增：ActivityStats 页面
        composable<Dest.ActivityStats> {
            // 先用 demo 数据，之后你可以换成 ViewModel 里的 state
            val demo = ActivityStatsUiState(
                period = StatsPeriod.DAY,
                dateLabel = "Today",
                totalDurationMinutes = 54,
                totalDistanceKm = 4.32f,
                caloriesIntake = 1850,
                caloriesBurned = 420,
                distancePoints = listOf(0.1f, 0.4f, 0.7f, 0.5f, 0.9f, 0.6f),
                caloriesBurnedPoints = listOf(120f, 180f, 160f, 220f, 210f, 190f, 230f),
                stepsPoints = listOf(0.2f, 0.5f, 0.7f, 0.6f, 0.9f, 0.8f),
                currentSteps = 7543,
                goalSteps = 10000
            )

            ActivityStatsScreen(
                uiState = demo,
                onBackClick = { navController.popBackStack() },
                onPeriodChange = { /* 之后接 ViewModel 切 Day/Week */ }
            )
        }

    }
}
