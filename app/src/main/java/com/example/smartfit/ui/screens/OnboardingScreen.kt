package com.example.smartfit.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.smartfit.AppContainer
import com.example.smartfit.ui.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(navController: NavHostController, appContainer: AppContainer) {
    // placeholder UI: choose a step goal and continue
    var goal by remember { mutableStateOf(5000f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Welcome to SmartFit", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        Text("Choose daily step goal: ${goal.toInt()} steps")
        Slider(value = goal, onValueChange = { goal = it }, valueRange = 1000f..20000f, steps = 9)
        Spacer(Modifier.height(16.dp))
        Button(onClick = {
            // save to DataStore via ProfileRepository / simple placeholder
            // In starter code we don't implement full DataStore write; show navigation
            CoroutineScope(Dispatchers.IO).launch {
                // Ideally call a ViewModel to save preferences. Placeholder: navigate.
            }
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Onboarding.route) { inclusive = true }
            }
        }) {
            Text("Let's start")
        }
    }
}
