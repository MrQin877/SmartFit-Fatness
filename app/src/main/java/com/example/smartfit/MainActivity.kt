package com.example.smartfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import com.example.smartfit.data.datastore.UserPreferences
import com.example.smartfit.ui.navigation.NavGraph
import com.example.smartfit.ui.theme.SmartFitTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val container by lazy { (application as SmartFitApplication).appContainer }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this // ← context available here

        // 🔧 TEMPORARY RESET (run once)
        /*lifecycleScope.launch {
            UserPreferences.setOnboarded(context, false)
            UserPreferences.setLoggedIn(context, false)
        }*/

        setContent {
            val ctx = LocalContext.current
            val themeMode by UserPreferences.getTheme(ctx).collectAsState(initial = "SYSTEM")

            val darkTheme = when (themeMode) {
                "DARK" -> true
                "LIGHT" -> false
                else -> isSystemInDarkTheme()
            }

            SmartFitTheme(darkTheme = darkTheme) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    NavGraph(appContainer = container)
                }
            }
        }
    }
}
