package com.example.smartfit.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.smartfit.AppContainer
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.MaterialTheme
import com.example.smartfit.ui.theme.LogDetailBackground


@Composable
fun LogDetailScreen(
    navController: NavHostController,
    appContainer: AppContainer,
    id: Long
) {
    // TODO: load from Room using id
    val log = remember(id) {
        DemoLogUi(
            title = "Morning Run",
            type = "Running",
            durationMin = 45,
            distanceKm = 8.50,
            calories = 430,
            createdAt = LocalDate.of(2025, 4, 20)
        )
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
            // Background image (replace with your asset or keep just the scrim)
            // Add a drawable named bg_gym_pattern (PNG/SVG) or remove this Image
            LogDetailBackground()
            // Dark scrim so foreground pops
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

            // Top actions
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
                TextButton(onClick = { /* TODO: navigate to edit screen */ }) {
                    Text(
                        "Edit",
                        color = MaterialTheme.colorScheme.onPrimary)
                }
            }

            // Center badge + title
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
                    text = log.title,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.2.sp
                    )
                )
            }
        }

        // ---------- Body cards ----------
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(Modifier.height(16.dp))

            // Type (with chevron affordance)
            ValueCard(
                label = "Type",
                value = log.type,
                trailing = {
                    Text(
                        text = ">",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(end = 6.dp)
                    )
                }
            )

            Spacer(Modifier.height(12.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ValueCard(
                    label = "Duration (min)",
                    value = "${log.durationMin}",
                    modifier = Modifier.weight(1f)
                )
                ValueCard(
                    label = "Distance (km)",
                    value = String.format("%.2f", log.distanceKm ?: 0.0),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(12.dp))

            ValueCard(
                label = "Calories (kcal)",
                value = "${log.calories}"
            )

            Spacer(Modifier.height(12.dp))

            // Delete
            OutlinedButton(
                onClick = { /* TODO */ },
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                ),
                border = BorderStroke(1.dp, SolidColor(MaterialTheme.colorScheme.error)), // ← SolidColor
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Delete")
            }

            Spacer(Modifier.height(16.dp))
            val dateStr = log.createdAt.format(DateTimeFormatter.ofPattern("yyyy MMM dd"))
            Text(
                text = "Added at $dateStr",
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
    modifier: Modifier = Modifier,
    trailing: (@Composable () -> Unit)? = null
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(Modifier.padding(horizontal = 18.dp, vertical = 14.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    label,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                trailing?.invoke()
            }
            Spacer(Modifier.height(6.dp))
            Text(
                value,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium)
            )
        }
    }
}

private data class DemoLogUi(
    val title: String,
    val type: String,
    val durationMin: Int,
    val distanceKm: Double?,
    val calories: Int,
    val createdAt: LocalDate
)
