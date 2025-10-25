package com.example.smartfit.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.smartfit.AppContainer

@Composable
fun AddLogScreen(navController: NavHostController, appContainer: AppContainer) {
    var title by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Add Activity", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
        Spacer(Modifier.height(12.dp))
        Button(onClick = {
            // TODO: Save via repository
            navController.navigateUp()
        }) {
            Text("Save")
        }
    }
}
