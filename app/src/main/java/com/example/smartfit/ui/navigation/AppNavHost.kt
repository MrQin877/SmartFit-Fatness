// app/src/main/java/com/example/smartfit/ui/navigation/AppNavHost.kt
package com.example.smartfit.ui.navigation

import androidx.activity.ComponentActivity
import android.graphics.Color as AColor
import androidx.activity.SystemBarStyle
import androidx.activity.compose.LocalActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavDestination.Companion.hasRoute
import com.example.smartfit.di.AppGraph
import com.example.smartfit.ui.theme.ThemeViewModel


@Composable
fun AppNavHost(graph: AppGraph) {
    val navController = rememberNavController()

    // --- Session + theme via repositories (no DataStore in UI)
    val prefs = graph.prefsRepo
    val isOnboarded by prefs.isOnboarded().collectAsStateWithLifecycle(initialValue = null)
    val isLoggedIn  by prefs.isLoggedIn().collectAsStateWithLifecycle(initialValue = null)

    // Theme through a tiny VM (or collect directly if you prefer)
    val themeVm = remember { ThemeViewModel(prefs) }
    val mode by themeVm.themeMode.collectAsStateWithLifecycle()
    val darkTheme = when (mode) {
        "DARK"  -> true
        "LIGHT" -> false
        else    -> isSystemInDarkTheme()
    }

    // Route-aware system bars
    SystemBarsForRoute(navController = navController, darkTheme = darkTheme)

    // Wait until prefs flows emit at least once
    if (isOnboarded == null || isLoggedIn == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(strokeWidth = 4.dp)
        }
        return
    }

    // Type-safe start destination
    val startDest: Dest = when {
        isOnboarded == false -> Dest.Onboarding
        isLoggedIn  == false -> Dest.Login
        else                 -> Dest.Home
    }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val destination = backStackEntry?.destination
    val showBottomBar = destination?.hierarchy?.any {
        it.hasRoute<Dest.Home>() || it.hasRoute<Dest.Logs>() ||
                it.hasRoute<Dest.Tips>() || it.hasRoute<Dest.Profile>()
    } == true

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                PillBottomBarWithFab( // keep your existing component
                    navController = navController,
                    onFabClick = { navController.navigate(Dest.AddLog) }
                )
            }
        }
    ) { padding ->
        NavGraphContent(
            navController = navController,
            graph = graph,
            innerPadding = padding,
            startDestination = startDest,
            isDark = darkTheme
        )
    }
}

/**
 * Central, route-aware system bar controller.
 * - Forces light icons on Login/SignUp (image backgrounds).
 * - Else follows app theme.
 */
@Composable
private fun SystemBarsForRoute(
    navController: NavHostController,
    darkTheme: Boolean
) {
    val activity = LocalActivity.current as? ComponentActivity ?: return
    val backStack by navController.currentBackStackEntryAsState()
    val dest = backStack?.destination

    LaunchedEffect(dest, darkTheme) {
        val darkScrim = AColor.argb(0x66, 0, 0, 0)
        val forceLightIcons =
            dest?.hasRoute<Dest.Login>() == true || dest?.hasRoute<Dest.SignUp>() == true

        if (forceLightIcons || darkTheme) {
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
