// app/src/main/java/com/example/smartfit/ui/theme/Theme.kt
package com.example.smartfit.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary      = DarkPrimary,
    secondary    = DarkSecondary,
    background   = DarkBackground,
    surface      = DarkSurface,
    onBackground = DarkOnBackground,
    onSurface    = DarkOnSurface,
    outline      = DarkOutline
)

private val LightColorScheme = lightColorScheme(
    primary      = LightPrimary,
    secondary    = LightSecondary,
    background   = LightBackground,
    surface      = LightSurface,
    onBackground = LightOnBackground,
    onSurface    = LightOnSurface,
    outline      = LightOutline
)

@Composable
fun SmartFitTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = SmartFitTypography,
        content = content
    )
}
