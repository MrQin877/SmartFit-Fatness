package com.example.smartfit.ui.navigation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.NavDestination.Companion.hasRoute

@Composable
fun PillBottomBarWithFab(
    navController: NavHostController,
    onFabClick: () -> Unit
) {
    val backStack by navController.currentBackStackEntryAsState()
    val current = backStack?.destination
    val accent = MaterialTheme.colorScheme.primary

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 14.dp, vertical = 8.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        // Pill container
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(74.dp)
                .shadow(12.dp, RoundedCornerShape(28.dp), clip = false)
                .clip(RoundedCornerShape(28.dp))
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BarItem(
                    label = "Home",
                    selected = current.isSelected<Dest.Home>(),
                    onClick = { navController.navigateSingleTopTo(Dest.Home) },
                    icon = { Icon(Icons.Default.Home, null) },
                    accent = accent,
                    modifier = Modifier.weight(1f)
                )
                BarItem(
                    label = "Activity",
                    selected = current.isSelected<Dest.Logs>(),
                    onClick = { navController.navigateSingleTopTo(Dest.Logs) },
                    icon = { Icon(Icons.AutoMirrored.Filled.List, null) },
                    accent = accent,
                    modifier = Modifier.weight(1f)
                )

                // space under the (+)
                Spacer(Modifier.width(60.dp))

                BarItem(
                    label = "Tips",
                    selected = current.isSelected<Dest.Tips>(),
                    onClick = { navController.navigateSingleTopTo(Dest.Tips) },
                    icon = { Icon(Icons.Default.Lightbulb, null) },
                    accent = accent,
                    modifier = Modifier.weight(1f)
                )
                BarItem(
                    label = "Profile",
                    selected = current.isSelected<Dest.Profile>(),
                    onClick = { navController.navigateSingleTopTo(Dest.Profile) },
                    icon = { Icon(Icons.Default.Person, null) },
                    accent = accent,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // soft glow (notch illusion)
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 10.dp)
                .size(50.dp)
                .clip(CircleShape)
                .background(accent.copy(alpha = 0.16f))
                .blur(18.dp)
        )

        // Center (+) with ring
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 10.dp)
                .size(52.dp)
                .clip(CircleShape)
                .border(BorderStroke(2.dp, MaterialTheme.colorScheme.outline), CircleShape)
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            FloatingActionButton(
                onClick = onFabClick,
                containerColor = accent,
                contentColor = Color.Black,
                shape = CircleShape,
                modifier = Modifier.size(50.dp),
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp,
                    focusedElevation = 0.dp,
                    hoveredElevation = 0.dp
                )
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add", modifier = Modifier.size(28.dp))
            }
        }
    }
}

@Composable
private fun BarItem(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    accent: Color,
    modifier: Modifier = Modifier
) {
    val tint = if (selected) accent else MaterialTheme.colorScheme.onSurfaceVariant
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(top = 10.dp, bottom = 8.dp)
    ) {
        CompositionLocalProvider(LocalContentColor provides tint) { icon() }
        Text(
            label,
            fontSize = 12.sp,
            color = tint,
            maxLines = 1,
            overflow = TextOverflow.Clip
        )
    }
}

/* -------- type-safe helpers -------- */

private inline fun <reified T : Dest> NavDestination?.isSelected(): Boolean =
    this?.hierarchy?.any { it.hasRoute<T>() } == true

private fun NavHostController.navigateSingleTopTo(dest: Dest) {
    navigate(dest) {
        launchSingleTop = true
        popUpTo(graph.findStartDestination().id) { saveState = true }
        restoreState = true
    }
}
