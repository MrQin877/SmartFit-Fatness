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

@Composable
fun LogDetailScreen(navController: NavHostController, appContainer: AppContainer, id: Long) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Log detail (id = $id)", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        Text("Placeholder for log details and edit functionality.")
        Spacer(Modifier.height(16.dp))
        Button(onClick = { navController.popBackStack() }) { Text("Back") }
    }
}
