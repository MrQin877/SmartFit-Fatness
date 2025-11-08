package com.example.smartfit.ui.logs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.smartfit.ui.AppViewModelProvider
import com.example.smartfit.ui.common.LogDetailBackground
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun LogDetailScreen(
    navController: NavHostController,
    isDark: Boolean
) {

    val vm: LogDetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val log = vm.log.collectAsState().value

    if (log == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val scroll = rememberScrollState()
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scroll)
    ) {
        // ---------- Header ----------
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
        ) {
            LogDetailBackground(isDark)

            Box(
                Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            0f to MaterialTheme.colorScheme.background.copy(alpha = 0.10f),
                            0.6f to MaterialTheme.colorScheme.background.copy(alpha = 0.60f),
                            1f to MaterialTheme.colorScheme.background
                        )
                    )
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                TextButton(onClick = { /* TODO: navigate to edit */ }) {
                    Text("Edit", color = MaterialTheme.colorScheme.onPrimary)
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.15f))
                        .shadow(8.dp, CircleShape, clip = false),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(108.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.DirectionsRun,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(56.dp)
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))
                Text(
                    text = log.type.orEmpty(),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.2.sp
                    )
                )
            }
        }

        // ---------- Body ----------
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(Modifier.height(16.dp))

            ValueCard("Duration (min)", "${log.durationMin ?: 0}")
            Spacer(Modifier.height(12.dp))

            ValueCard("Calories (kcal)", "${log.calories ?: 0}")
            Spacer(Modifier.height(12.dp))

            ValueCard("Notes", log.notes ?: "-")
            Spacer(Modifier.height(12.dp))

            OutlinedButton(
                onClick = { /* TODO delete via VM if you add API */ },
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                ),
                border = BorderStroke(1.dp, SolidColor(MaterialTheme.colorScheme.error)),
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier.fillMaxWidth()
            ) { Text("Delete") }

            Spacer(Modifier.height(16.dp))
            val dateText = runCatching {
                LocalDate.parse(log.date).format(DateTimeFormatter.ofPattern("yyyy MMM dd"))
            }.getOrElse { log.date }
            Text(
                text = "Added at $dateText",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(start = 4.dp, bottom = 24.dp)
            )
        }
    }
}

@Composable
private fun ValueCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(Modifier.padding(horizontal = 18.dp, vertical = 14.dp)) {
            Text(
                label,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(6.dp))
            Text(
                value,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium)
            )
        }
    }
}
