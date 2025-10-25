package com.example.smartfit.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.smartfit.AppContainer
import com.example.smartfit.ui.navigation.Screen
import kotlinx.coroutines.launch
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.smartfit.data.datastore.dataStore

@Composable
fun LoginScreen(navController: NavHostController, appContainer: AppContainer) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val dataStore = context.dataStore
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Log In", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = password, onValueChange = { password = it },
            label = { Text("Password") }, visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.height(16.dp))
        Button(onClick = {
            scope.launch {
                val user = appContainer.userRepository.login(email, password)
                if (user != null) {
                    dataStore.edit { it[booleanPreferencesKey("is_logged_in")] = true }
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                } else message = "Invalid email or password"
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Log In")
        }

        Spacer(Modifier.height(8.dp))
        TextButton(onClick = { navController.navigate(Screen.SignUp.route) }) {
            Text("No account? Sign Up")
        }

        message?.let {
            Spacer(Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }
    }
}
