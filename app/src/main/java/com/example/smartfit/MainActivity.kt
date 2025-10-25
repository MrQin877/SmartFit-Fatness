package com.example.smartfit

import android.graphics.Color as AColor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.smartfit.data.datastore.UserPreferences
import com.example.smartfit.ui.navigation.AppNavHost
import com.example.smartfit.ui.theme.SmartFitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val ctx = LocalContext.current
            val mode by UserPreferences.getTheme(ctx).collectAsState(initial = "SYSTEM")
            val darkTheme = when (mode) {
                "DARK" -> true
                "LIGHT" -> false
                else    -> isSystemInDarkTheme()
            }


            LaunchedEffect(darkTheme) {
                val transparent = AColor.TRANSPARENT
                val darkScrim   = AColor.argb(0x66, 0, 0, 0)   // ~40% black

                this@MainActivity.enableEdgeToEdge(
                    statusBarStyle =
                        if (darkTheme)
                            SystemBarStyle.dark(transparent)           // light icons
                        else
                            SystemBarStyle.light(transparent, darkScrim), // dark icons
                    navigationBarStyle =
                        if (darkTheme)
                            SystemBarStyle.dark(transparent)
                        else
                            SystemBarStyle.light(transparent, darkScrim)
                )
            }

            SmartFitTheme(darkTheme = darkTheme) {
                Surface(modifier = Modifier) {
                    AppNavHost(appContainer = (application as SmartFitApplication).appContainer)
                }
            }
        }
    }
}