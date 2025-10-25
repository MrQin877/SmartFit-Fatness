package com.example.smartfit.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.smartfit.AppContainer
import com.example.smartfit.data.datastore.UserPreferences
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(navController: NavHostController, appContainer: AppContainer) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Load current preferences from DataStore
    val themeMode by UserPreferences.getTheme(context).collectAsState(initial = "SYSTEM")
    val stepGoal by UserPreferences.getStepGoal(context).collectAsState(initial = 8000)

    // Local state for UI interaction
    var goal by remember { mutableStateOf(stepGoal.toFloat()) }
    var isDarkTheme by remember { mutableStateOf(themeMode == "DARK") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text("Profile / Settings", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(12.dp))

            // 🌙 Theme toggle
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Dark theme")
                Spacer(Modifier.width(8.dp))
                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = {
                        isDarkTheme = it
                        scope.launch {
                            UserPreferences.setTheme(context, if (it) "DARK" else "LIGHT")
                        }
                    }
                )
            }

            Spacer(Modifier.height(12.dp))

            // 🚶 Step goal slider
            Text("Daily step goal: ${goal.toInt()}")
            Slider(
                value = goal,
                onValueChange = { goal = it },
                onValueChangeFinished = {
                    scope.launch {
                        UserPreferences.setStepGoal(context, goal.toInt())
                    }
                },
                valueRange = 1000f..20000f
            )
        }

        // 🚪 Logout button at bottom
        Button(
            onClick = {
                scope.launch {
                    UserPreferences.setLoggedIn(context, false)
                }
                navController.navigate("login") {
                    popUpTo("home") { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Logout", color = MaterialTheme.colorScheme.onError)
        }
    }
}
