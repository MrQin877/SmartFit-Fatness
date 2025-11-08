// app/src/main/java/com/example/smartfit/ui/profile/ProfileScreen.kt
package com.example.smartfit.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.smartfit.ui.AppViewModelProvider
import com.example.smartfit.ui.navigation.Dest

@Composable
fun ProfileScreen(
    navController: NavHostController
) {
    val vm: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val theme = vm.themeMode.collectAsState().value          // "SYSTEM" | "DARK" | "LIGHT"
    val goal  = vm.stepGoal.collectAsState().value           // Int

    // UI editing state derived from VM state
    var isDark by remember(theme) { mutableStateOf(theme == "DARK") }
    var goalEditing by remember(goal) { mutableStateOf(goal.toFloat()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text("Profile", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))

        HeaderCard(
            name = "LamCong",
            avatarUrl = "https://i.pravatar.cc/150?img=12",
            onEdit = { /* TODO: navigate to edit profile */ }
        )

        Spacer(Modifier.height(14.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            item {
                AccountCard(email = "lamcong@gmail.com")
                Spacer(Modifier.height(14.dp))
            }
            item {
                SettingsCard(
                    isDark = isDark,
                    onDarkChange = { checked ->
                        isDark = checked
                        vm.setTheme(if (checked) "DARK" else "LIGHT")
                    },
                    goal = goalEditing,
                    onGoalChange = { goalEditing = it.coerceIn(1000f, 20000f) },
                    onGoalCommit = { vm.setStepGoal(goalEditing.toInt()) }
                )
                Spacer(Modifier.height(18.dp))
            }
            item {
                LogoutButton(
                    onLogout = {
                        vm.logout()
                        navController.navigate(Dest.Login) {
                            popUpTo(Dest.Home) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

/* ---------- Building blocks ---------- */

@Composable
private fun HeaderCard(
    name: String,
    avatarUrl: String,
    onEdit: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(avatarUrl),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text(name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
                Text(
                    "✏️ Edit Profile",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.clickable(onClick = onEdit)
                )
            }
        }
    }
}

@Composable
private fun AccountCard(email: String) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp)) {
            Text("Account", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(
                value = email,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            )
        }
    }
}

@Composable
private fun SettingsCard(
    isDark: Boolean,
    onDarkChange: (Boolean) -> Unit,
    goal: Float,
    onGoalChange: (Float) -> Unit,
    onGoalCommit: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Dark theme", style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                Switch(
                    checked = isDark,
                    onCheckedChange = onDarkChange,
                    colors = SwitchDefaults.colors(
                        uncheckedThumbColor = MaterialTheme.colorScheme.surface,
                        uncheckedTrackColor = MaterialTheme.colorScheme.secondary,
                        uncheckedBorderColor = Color.Transparent,
                        uncheckedIconColor = MaterialTheme.colorScheme.secondary
                    )
                )
            }

            Spacer(Modifier.height(16.dp))

            Text("Daily step goal: ${goal.toInt()}", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Slider(
                value = goal,
                onValueChange = onGoalChange,
                onValueChangeFinished = onGoalCommit,
                valueRange = 1000f..20000f,
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.primary,
                    inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                    inactiveTickColor = MaterialTheme.colorScheme.outlineVariant
                )
            )
        }
    }
}

@Composable
private fun LogoutButton(onLogout: () -> Unit) {
    Button(
        onClick = onLogout,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(26.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.errorContainer,
            contentColor = MaterialTheme.colorScheme.onErrorContainer
        )
    ) { Text("Logout") }
}
