// app/src/main/java/com/example/smartfit/ui/screens/AddLogScreen.kt
package com.example.smartfit.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.smartfit.AppContainer
import com.example.smartfit.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLogScreen(
    navController: NavHostController,
    appContainer: AppContainer
) {
    // ----- State -----
    var title by remember { mutableStateOf("Evening Run") }
    var type by remember { mutableStateOf("Running") }
    var duration by remember { mutableStateOf("45") }
    var distance by remember { mutableStateOf("8.50") }
    var calories by remember { mutableStateOf("430") }

    val recent = listOf("Evening Run", "Morning Run", "Evening Run")
    val typeOptions = listOf("Running", "Walking", "Cycling", "Gym", "Yoga", "Other")
    var typeMenu by remember { mutableStateOf(false) }

    val canSave = title.isNotBlank()

    Scaffold(
        containerColor = Color.Transparent,
        // ✅ Pinned Save button that lifts above keyboard & system nav bar
        bottomBar = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .imePadding()
                    .navigationBarsPadding()
                    .padding(horizontal = 24.dp, vertical = 12.dp)
            ) {
                Button(
                    onClick = {
                        // TODO: persist to Room via repository:
                        // appContainer.activityRepository.insert(...)
                        // Optionally set a result for snackbar on Logs screen:
                        navController.currentBackStackEntry
                            ?.savedStateHandle
                            ?.set("saved_activity_title", title)

                        // Slide sheet out then go to Activity tab
                        navController.popBackStack()
                        navController.navigate(Screen.Logs.route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(Screen.Home.route) { saveState = true }
                        }
                    },
                    enabled = canSave,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(66.dp)
                        .clip(RoundedCornerShape(26.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.Black
                    )
                ) {
                    Text("Save Activity", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    ) { padding ->
        // ✅ Scrollable rounded “sheet” content
        Column(
            Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(top = 24.dp, start = 12.dp, end = 12.dp)
                .shadow(12.dp, RoundedCornerShape(30.dp), clip = false)
                .clip(RoundedCornerShape(30.dp))
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.92f))
                .border(
                    BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                    RoundedCornerShape(30.dp)
                )
                .padding(horizontal = 22.dp, vertical = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Box(Modifier.fillMaxWidth()) {
                Text(
                    "New Activity",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.2.sp
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) { Icon(Icons.Default.Close, contentDescription = "Close") }
            }

            Spacer(Modifier.height(16.dp))

            Text(
                "Recent activities",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(10.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(recent) { chip ->
                    AssistChip(
                        onClick = { title = chip },
                        label = { Text(chip) },
                        shape = RoundedCornerShape(22.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                }
            }

            Divider(
                modifier = Modifier.padding(top = 18.dp, bottom = 16.dp),
                color = MaterialTheme.colorScheme.outlineVariant
            )

            // Activity Name
            FieldCard(label = "Activity Name") {
                BorderlessInput(
                    value = title,
                    onValueChange = { title = it },
                    singleLine = true
                )
            }

            Spacer(Modifier.height(12.dp))

            // Type (card with lime chevron)
            FieldCard(label = "Type") {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 6.dp)
                        .clickable { typeMenu = true }
                ) {
                    Text(
                        text = type,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                    Text(
                        text = ">",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }
                DropdownMenu(
                    expanded = typeMenu,
                    onDismissRequest = { typeMenu = false }
                ) {
                    typeOptions.forEach {
                        DropdownMenuItem(
                            text = { Text(it) },
                            onClick = { type = it; typeMenu = false }
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            // Duration & Distance
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                FieldCard(label = "Duration (min)", modifier = Modifier.weight(1f)) {
                    BorderlessInput(
                        value = duration,
                        onValueChange = { v -> if (v.all(Char::isDigit)) duration = v },
                        singleLine = true,
                        keyboardType = KeyboardType.Number
                    )
                }
                FieldCard(label = "Distance (km)", modifier = Modifier.weight(1f)) {
                    BorderlessInput(
                        value = distance,
                        onValueChange = { v ->
                            if (v.matches("""\d*\.?\d{0,2}""".toRegex())) distance = v
                        },
                        singleLine = true,
                        keyboardType = KeyboardType.Decimal
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            // Calories
            FieldCard(label = "Calories (kcal)") {
                BorderlessInput(
                    value = calories,
                    onValueChange = { v -> if (v.all(Char::isDigit)) calories = v },
                    singleLine = true,
                    keyboardType = KeyboardType.Number
                )
            }

            // Bottom padding so last card doesn't hide behind the fixed Save bar
            Spacer(Modifier.height(24.dp))
        }
    }
}

/* ---------- Helpers ---------- */

@Composable
private fun FieldCard(
    label: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier) {
        Text(
            label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(8.dp))
        Card(
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
        ) {
            // Single outer border; inner input is borderless
            Box(Modifier.padding(horizontal = 18.dp, vertical = 14.dp)) { content() }
        }
    }
}

@Composable
private fun BorderlessInput(
    value: String,
    onValueChange: (String) -> Unit,
    singleLine: Boolean,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = singleLine,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        textStyle = LocalTextStyle.current.copy(
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium
        ),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.onSurface
        )
    )
}
