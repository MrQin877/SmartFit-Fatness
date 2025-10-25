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
import com.example.smartfit.ui.navigation.Screen
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(navController: NavHostController, appContainer: AppContainer) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Welcome to SmartFit",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(Modifier.height(16.dp))
        Text(
            "Track your steps, calories, and workouts easily.\nStay motivated every day!",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(32.dp))

        // ✅ Continue button
        Button(
            onClick = {
                scope.launch {
                    UserPreferences.setOnboarded(context, true)
                }
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Onboarding.route) { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            Text("Continue")
        }

        Spacer(Modifier.height(16.dp))

        // ✅ Skip button
        TextButton(
            onClick = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Onboarding.route) { inclusive = true }
                }
            }
        ) {
            Text("Skip for now", color = MaterialTheme.colorScheme.primary)
        }
    }
}

