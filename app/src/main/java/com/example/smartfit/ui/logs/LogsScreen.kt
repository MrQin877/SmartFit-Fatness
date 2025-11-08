package com.example.smartfit.ui.logs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.smartfit.data.model.ActivityLog
import com.example.smartfit.ui.AppViewModelProvider
import com.example.smartfit.ui.navigation.Dest

@Composable
fun LogsScreen(
    navController: NavHostController,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {

    val vm: LogsViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val ui = vm.state.collectAsState().value

    var selectedFilter by remember { mutableStateOf("All") } // keep for future filters

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(12.dp))
        Text(
            text = "My Activities",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.SemiBold)
        )
        Spacer(Modifier.height(16.dp))

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
            items(ui.items, key = { it.id ?: it.hashCode().toLong() }) { log ->
                ActivityLogRow(
                    log = log,
                    onClick = {
                        val id = log.id ?: return@ActivityLogRow
                        navController.navigate(Dest.LogDetail(id))
                    }
                )
            }
            item { Spacer(Modifier.height(120.dp)) }
        }
    }
}

@Composable
private fun ActivityLogRow(log: ActivityLog, onClick: () -> Unit) {
    val distance = log.notes?.takeIf { it.isNotBlank() } ?: "-" // adapt if you add distance field
    val duration = (log.durationMin ?: 0).let { "$it min" }
    val calories = (log.calories ?: 0).let { "$it kcal" }
    val date = log.date

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
                Text(log.type.orEmpty(), fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "$distance - $duration - $calories",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = date,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
