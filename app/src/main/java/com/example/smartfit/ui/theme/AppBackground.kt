package com.example.smartfit.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.smartfit.R
import com.example.smartfit.data.datastore.UserPreferences

/**
 * Background for the Log Detail screen
 */
@Composable
fun LogDetailBackground() {
    val ctx = LocalContext.current
    val themeMode by UserPreferences.getTheme(ctx).collectAsState(initial = "SYSTEM")
    val darkTheme = when (themeMode) {
        "DARK" -> true
        "LIGHT" -> false
        else -> isSystemInDarkTheme()
    }

    val painter = painterResource(
        id = if (darkTheme)
            R.drawable.detail_bg_pattern_dark
        else
            R.drawable.detail_bg_pattern_light
    )

    Box(Modifier.fillMaxSize()) {
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        // Gentle gradient scrim for text contrast
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = if (darkTheme)
                            listOf(
                                Color.Black.copy(alpha = 0.10f),
                                Color.Black.copy(alpha = 0.45f),
                                Color.Black.copy(alpha = 0.70f)
                            )
                        else
                            listOf(
                                Color.White.copy(alpha = 0.06f),
                                Color.White.copy(alpha = 0.20f),
                                Color.White.copy(alpha = 0.55f)
                            )
                    )
                )
        )
    }
}

/**
 * Background for the Register (Sign Up) screen
 */
// ui/theme/AppBackground.kt
@Composable
fun RegisterBackground() {
    val ctx = LocalContext.current
    val themeMode by UserPreferences.getTheme(ctx).collectAsState(initial = "SYSTEM")
    val darkTheme = when (themeMode) {
        "DARK" -> true
        "LIGHT" -> false
        else -> isSystemInDarkTheme()
    }

    val resId = if (darkTheme)
        R.drawable.register_bg_pattern_dark
    else
        R.drawable.register_bg_pattern_light

    Box(Modifier.fillMaxSize()) {
        // Full-bleed image
        Image(
            painter = painterResource(resId),
            contentDescription = null,
            // Try Crop first (preserves aspect). If your light image still leaves gaps, switch to FillBounds.
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )

        // Readability scrim
        Box(
            Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colors = if (darkTheme)
                            listOf(
                                Color.Black.copy(0.10f),
                                Color.Black.copy(0.45f),
                                Color.Black.copy(0.70f)
                            )
                        else
                            listOf(
                                Color.Black.copy(0.5f),
                                Color.Black.copy(0.5f),
                                Color.Black.copy(0.5f)
                            )
                    )
                )
        )
    }
}

@Composable
fun LoginBackground() {
    val ctx = LocalContext.current
    val themeMode by UserPreferences.getTheme(ctx).collectAsState(initial = "SYSTEM")
    val darkTheme = when (themeMode) {
        "DARK" -> true
        "LIGHT" -> false
        else -> isSystemInDarkTheme()
    }

    val resId = if (darkTheme)
        R.drawable.login_bg_pattern_dark
    else
        R.drawable.login_bg_pattern_light

    Box(Modifier.fillMaxSize()) {
        // Full-bleed image
        Image(
            painter = painterResource(resId),
            contentDescription = null,
            // Try Crop first (preserves aspect). If your light image still leaves gaps, switch to FillBounds.
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )

        // Readability scrim
        Box(
            Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colors = if (darkTheme)
                            listOf(
                                Color.Black.copy(0.10f),
                                Color.Black.copy(0.45f),
                                Color.Black.copy(0.70f)
                            )
                        else
                            listOf(
                                Color.Black.copy(0.5f),
                                Color.Black.copy(0.5f),
                                Color.Black.copy(0.5f)
                            )
                    )
                )
        )
    }
}

