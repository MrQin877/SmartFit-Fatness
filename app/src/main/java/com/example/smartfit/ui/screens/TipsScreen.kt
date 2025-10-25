package com.example.smartfit.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.smartfit.AppContainer
import com.example.smartfit.ui.navigation.Screen

@Composable
fun TipsScreen(navController: NavHostController, appContainer: AppContainer) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Tips (network suggestions)", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        Text("This screen will call the TipsRepository to fetch tips and show them.")
        Spacer(Modifier.height(16.dp))
        Button(onClick = { navController.navigate(Screen.AddLog.route) }) {
            Text("Log a tip (prefill demo)")
        }
    }
}
