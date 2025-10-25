// app/src/main/java/com/example/smartfit/ui/navigation/AppNavHost.kt
package com.example.smartfit.ui.navigation

import android.graphics.Color as AColor
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.smartfit.AppContainer
import com.example.smartfit.data.datastore.UserPreferences


@Composable
fun AppNavHost(appContainer: AppContainer) {
    val navController = rememberNavController()
    val ctx = LocalContext.current

    // read onboarding/login flags
    val isOnboarded by UserPreferences.getOnboarded(ctx)
        .collectAsStateWithLifecycle(initialValue = null)
    val isLoggedIn by UserPreferences.getLoggedIn(ctx)
        .collectAsStateWithLifecycle(initialValue = null)

    // theme mode for route-aware system-bar styling
    val mode by UserPreferences.getTheme(ctx).collectAsState(initial = "SYSTEM")
    val darkTheme = when (mode) {
        "DARK"  -> true
        "LIGHT" -> false
        else    -> isSystemInDarkTheme()
    }

    // apply system bar styles based on current route
    SystemBarsForRoute(navController = navController, darkTheme = darkTheme)

    // loading while prefs initialize
    if (isOnboarded == null || isLoggedIn == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                // optional tuning
                strokeWidth = 4.dp
            )
        }
        return
    }

    // compute start destination (don’t navigate yet)
    val startDestination = when {
        isOnboarded == false -> Screen.Onboarding.route
        isLoggedIn == false  -> Screen.Login.route
        else                 -> Screen.Home.route
    }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val destination = backStackEntry?.destination
    val topLevelRoutes = listOf(
        Screen.Home.route, Screen.Logs.route, Screen.Tips.route, Screen.Profile.route
    )
    val showBottomBar =
        destination?.hierarchy?.any { dest -> dest.route in topLevelRoutes } == true

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                PillBottomBarWithFab(
                    navController = navController,
                    onFabClick = { navController.navigate(Screen.AddLog.route) }
                )
            }
        }
    ) { padding ->
        NavGraphContent(
            navController = navController,
            appContainer = appContainer,
            innerPadding = padding,
            startDestination = startDestination
        )
    }
}

/**
 * Central, route-aware system bar controller.
 * - Forces white icons on Login/SignUp (photo backgrounds).
 * - Else follows the app theme from DataStore.
 */
@Composable
private fun SystemBarsForRoute(
    navController: NavHostController,
    darkTheme: Boolean
) {
    val activity = LocalContext.current as ComponentActivity
    val backStack by navController.currentBackStackEntryAsState()
    val route = backStack?.destination?.route

    LaunchedEffect(route, darkTheme) {
        val darkScrim = AColor.argb(0x66, 0, 0, 0)

        val forceLightIcons = route == Screen.Login.route || route == Screen.SignUp.route

        if (forceLightIcons) {
            // white status & nav icons (good over dark-ish images)
            activity.enableEdgeToEdge(
                statusBarStyle     = SystemBarStyle.dark(AColor.TRANSPARENT),
                navigationBarStyle = SystemBarStyle.dark(AColor.TRANSPARENT)
            )
        } else {
            // follow app theme
            if (darkTheme) {
                activity.enableEdgeToEdge(
                    statusBarStyle     = SystemBarStyle.dark(AColor.TRANSPARENT),
                    navigationBarStyle = SystemBarStyle.dark(AColor.TRANSPARENT)
                )
            } else {
                activity.enableEdgeToEdge(
                    statusBarStyle     = SystemBarStyle.light(AColor.TRANSPARENT, darkScrim),
                    navigationBarStyle = SystemBarStyle.light(AColor.TRANSPARENT, darkScrim)
                )
            }
        }
    }
}
