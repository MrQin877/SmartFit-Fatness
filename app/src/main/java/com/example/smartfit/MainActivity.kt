package com.example.smartfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.smartfit.data.datastore.UserPreferences
import com.example.smartfit.ui.navigation.NavGraph
import com.example.smartfit.ui.theme.SmartFitTheme
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    private val container by lazy { (application as SmartFitApplication).appContainer }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            // Observe theme mode from DataStore
            val themeMode by UserPreferences.getTheme(context).collectAsState(initial = "SYSTEM")

            // Convert saved string to Boolean for SmartFitTheme
            val darkTheme = when (themeMode) {
                "DARK" -> true
                "LIGHT" -> false
                else -> isSystemInDarkTheme() // default system mode
            }

            SmartFitTheme(darkTheme = darkTheme) {
                Surface(modifier = Modifier) {
                    NavGraph(appContainer = container)
                }
            }
        }
    }
}
