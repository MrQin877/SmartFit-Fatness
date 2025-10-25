package com.example.smartfit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.smartfit.AppContainer
import com.example.smartfit.ui.navigation.Screen
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogsScreen(navController: NavHostController, appContainer: AppContainer) {
    // 🧩 Sample data for now (you’ll replace with DB data later)
    val sampleLogs = listOf(
        ActivityLogUi("Morning Run", "5.2 km", "30 min", "320 kcal", "2025-10-20"),
        ActivityLogUi("Evening Walk", "2.8 km", "25 min", "150 kcal", "2025-10-21"),
        ActivityLogUi("Gym Workout", "-", "45 min", "400 kcal", "2025-10-22"),
        ActivityLogUi("Cycling", "10.5 km", "40 min", "370 kcal", "2025-10-23")
    )

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddLog.route) },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Activity", tint = Color.White)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "My Activity",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(vertical = 12.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(sampleLogs) { log ->
                    ActivityLogItem(log) {
                        // 🔗 navigate to detail when clicked
                        navController.navigate(Screen.LogDetail.createRoute(log.hashCode().toLong()))
                    }
                }
            }
        }
    }
}

@Composable
fun ActivityLogItem(log: ActivityLogUi, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 🏃 Icon
            Icon(
                imageVector = Icons.Default.DirectionsRun,
                contentDescription = "Activity Icon",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(40.dp)
            )

            Spacer(Modifier.width(12.dp))

            // 🧠 Info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = log.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "${log.distance} • ${log.duration} • ${log.calories}",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }

            // 📅 Date (right aligned)
            Text(
                text = log.date,
                color = Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.Top)
            )
        }
    }
}

// Simple data holder for preview/demo
data class ActivityLogUi(
    val name: String,
    val distance: String,
    val duration: String,
    val calories: String,
    val date: String
)
