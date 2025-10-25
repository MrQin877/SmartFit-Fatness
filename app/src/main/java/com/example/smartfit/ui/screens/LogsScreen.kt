package com.example.smartfit.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.smartfit.AppContainer
import com.example.smartfit.ui.navigation.Screen


@Composable
fun LogsScreen(navController: NavHostController, appContainer: AppContainer) {
    // Placeholder sample list
    val sample = List(6) { index -> "Activity #${index + 1}" }
    Box(modifier = Modifier.fillMaxSize().padding(12.dp)) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(sample) { item ->
                ListItem(
                    headlineContent = { Text(item) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Screen.LogDetail.createRoute(item.hashCode().toLong()))
                        }
                )

            }
        }
        FloatingActionButton(onClick = { navController.navigate(Screen.AddLog.route)}, modifier = Modifier
            .align(androidx.compose.ui.Alignment.BottomEnd)
            .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }
}
