package com.example.smartfit.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.smartfit.AppContainer
import com.example.smartfit.data.datastore.UserPreferences
import com.example.smartfit.ui.navigation.Screen
import kotlinx.coroutines.launch
import com.example.smartfit.R

@Composable
fun OnboardingScreen(navController: NavHostController, appContainer: AppContainer) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // 👣 Keep track of which page the user is on
    var step by remember { mutableStateOf(1) }
    var goal by remember { mutableStateOf(8000f) }

    when (step) {
        1 -> {
            // --- STEP 1: Welcome Page ---
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // 🖼️ You can replace this image with your own drawable
                Image(
                    painter = painterResource(android.R.drawable.ic_menu_gallery), // Example placeholder
                    contentDescription = "Welcome image",
                    modifier = Modifier.size(160.dp)
                )
                Spacer(Modifier.height(24.dp))
                Text(
                    "Welcome to SmartFit!",
                    style = MaterialTheme.typography.headlineSmall,
                    fontSize = 28.sp
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    "Your personal fitness tracker to help you stay healthy and motivated every day.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )

                Spacer(Modifier.height(32.dp))

                Button(
                    onClick = { step = 2 },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Next")
                }

                Spacer(Modifier.height(8.dp))
                TextButton(
                    onClick = {
                        scope.launch {
                            UserPreferences.setOnboarded(context, true)
                        }
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Onboarding.route) { inclusive = true }
                        }
                    }
                ) {
                    Text("Skip for now")
                }
            }
        }

        2 -> {
            // --- STEP 2: Step Goal Setup ---
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Set Your Daily Step Goal", style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(16.dp))
                Text("Choose your daily target to stay motivated:")
                Spacer(Modifier.height(12.dp))

                Slider(
                    value = goal,
                    onValueChange = { goal = it },
                    valueRange = 1000f..20000f,
                    steps = 9,
                    modifier = Modifier.fillMaxWidth()
                )
                Text("${goal.toInt()} steps", style = MaterialTheme.typography.bodyLarge)

                Spacer(Modifier.height(24.dp))

                Button(
                    onClick = {
                        scope.launch {
                            UserPreferences.setStepGoal(context, goal.toInt())
                            UserPreferences.setOnboarded(context, true)
                        }
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Onboarding.route) { inclusive = true }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Continue")
                }

                Spacer(Modifier.height(8.dp))
                TextButton(
                    onClick = {
                        scope.launch {
                            UserPreferences.setOnboarded(context, true)
                        }
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Onboarding.route) { inclusive = true }
                        }
                    }
                ) {
                    Text("Skip for now")
                }
            }
        }
    }
}
