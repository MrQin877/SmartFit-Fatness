// app/src/main/java/com/example/smartfit/ui/navigation/BottomBar.kt
package com.example.smartfit.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState



@Composable
fun BottomBar(navController: NavHostController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDest = backStackEntry?.destination

    NavigationBar {
        NavigationBarItem(
            selected = currentDest.isSelected(Screen.Home.route),
            onClick = { navController.navigateSingleTopTo(Screen.Home.route) },
            icon = { androidx.compose.material3.Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = currentDest.isSelected(Screen.Logs.route),
            onClick = { navController.navigateSingleTopTo(Screen.Logs.route) },
            icon = { androidx.compose.material3.Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Activity") },
            label = { Text("Activity") }
        )
        NavigationBarItem(
            selected = currentDest.isSelected(Screen.Tips.route),
            onClick = { navController.navigateSingleTopTo(Screen.Tips.route) },
            icon = { androidx.compose.material3.Icon(Icons.Default.Lightbulb, contentDescription = "Tips") },
            label = { Text("Tips") }
        )
        NavigationBarItem(
            selected = currentDest.isSelected(Screen.Profile.route),
            onClick = { navController.navigateSingleTopTo(Screen.Profile.route) },
            icon = { androidx.compose.material3.Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") }
        )
    }
}

private fun NavDestination?.isSelected(route: String): Boolean =
    this?.hierarchy?.any { it.route == route } == true

private fun NavHostController.navigateSingleTopTo(route: String) {
    navigate(route) {
        launchSingleTop = true
        popUpTo(graph.findStartDestination().id) { saveState = true }
        restoreState = true
    }
}
