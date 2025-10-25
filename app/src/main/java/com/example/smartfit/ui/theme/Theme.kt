package com.example.smartfit.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color

// --- Color Schemes tuned to the mock ---

private val LightColorScheme = lightColorScheme(
    primary = LimeA400,            // accent for rings, buttons, selected icons
    onPrimary = Color.Black,       // black text on neon is readable (mock)
    secondary = Gray700,           // general emphasis text/icons
    onSecondary = Color.White,
    tertiary = Gray700,

    background = Color.White,
    onBackground = Color(0xFF111111),

    surface = Color.White,
    onSurface = Color(0xFF111111),

    surfaceVariant = Gray100,      // cards (“Daily Activity”, “Tips”)
    onSurfaceVariant = Color(0xFF5A5A5A),

    outline = Gray200,             // soft borders
    outlineVariant = Gray200
)

private val DarkColorScheme = darkColorScheme(
    primary = LimeA400_Dark,
    onPrimary = Color.Black,

    primaryContainer= Color(0xFF2A2A2A),

    secondary = Color(0xFFCCCCCC),
    onSecondary = Color.Black,

    background = Gray900,
    onBackground = Color(0xFFEDEDED),

    surface = Gray800,
    onSurface = Color(0xFFEDEDED),

    surfaceVariant = Color(0xFF1D1F22),
    onSurfaceVariant = Color(0xFFBDBDBD),

    outline = Color(0xFF3D3D3D),
    outlineVariant = Color(0xFF3D3D3D)
)

/**
 * Keep dynamicColor = false for consistent lime branding.
 * If you set it to true on Android 12+, system colors will override your lime.
 */
@Composable
fun SmartFitTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,   // <— IMPORTANT: keep false to match mock
    content: @Composable () -> Unit
) {
    val scheme =
        if (dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val ctx = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(ctx) else dynamicLightColorScheme(ctx)
        } else if (darkTheme) {
            DarkColorScheme
        } else {
            LightColorScheme
        }

    MaterialTheme(
        colorScheme = scheme,
        typography = Typography,
        content = content
    )
}
