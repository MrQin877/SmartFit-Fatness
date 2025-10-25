package com.example.smartfit.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.smartfit.AppContainer
import com.example.smartfit.ui.navigation.Screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsRun

import androidx.compose.runtime.Composable

@Composable
fun LogsScreen(
    navController: NavHostController,
    appContainer: AppContainer,
    contentPadding: PaddingValues = PaddingValues(0.dp) // <-- receive padding from AppNavHost
) {
    // TODO: replace with DB flow soon
    val sampleLogs = remember {
        listOf(
            ActivityLogUi("Morning Run", "4.56 km", "32 min", "290 kcal", "Apr 20"),
            ActivityLogUi("Evening Walk", "2.8 km", "25 min", "150 kcal", "Apr 21"),
            ActivityLogUi("Gym Workout", "-", "45 min", "400 kcal", "Apr 22"),
            ActivityLogUi("Cycling", "10.5 km", "40 min", "370 kcal", "Apr 23")
        )
    }

    var selectedFilter by remember { mutableStateOf("All") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)     // <- respect top-level scaffold/pill bar insets
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(12.dp))
        Text(
            text = "My Activities",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.SemiBold)
        )
        Spacer(Modifier.height(16.dp))

        // Filter "chip" matching the rounded field in your mock
        OutlinedCard(
            shape = RoundedCornerShape(22.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
            colors = CardDefaults.outlinedCardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(selectedFilter, style = MaterialTheme.typography.titleMedium)
            }
        }

        Spacer(Modifier.height(14.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(sampleLogs) { log ->
                ActivityLogRow(
                    log = log,
                    onClick = {
                        navController.navigate(Screen.LogDetail.createRoute(log.hashCode().toLong()))
                    }
                )
            }

            // Add safe space so last item clears the pill bar + big (+)
            item { Spacer(Modifier.height(120.dp)) }
        }
    }
}

@Composable
private fun ActivityLogRow(log: ActivityLogUi, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.DirectionsRun,
                contentDescription = "Activity",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )
            Spacer(Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                Text(log.name, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "${log.distance} - ${log.duration} - ${log.calories}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = log.date,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
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
