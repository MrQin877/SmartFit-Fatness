package com.example.smartfit.ui.screens

import android.app.Application
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.smartfit.AppContainer
import com.example.smartfit.ui.navigation.Screen
import kotlinx.coroutines.delay
import java.time.LocalTime
import kotlin.math.min
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material.icons.automirrored.filled.List



@Composable
fun HomeScreen(navController: NavHostController, appContainer: AppContainer) {
    var loaded by remember { mutableStateOf(false) }

    // trigger smooth appearance when entering screen
    LaunchedEffect(Unit) {
        delay(300)
        loaded = true
    }

    Scaffold(
        bottomBar = { BottomNavBar(navController) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(8.dp))

            AnimatedVisibility(visible = loaded) {
                ProfileGreeting()
            }

            Spacer(Modifier.height(16.dp))

            AnimatedVisibility(visible = loaded) {
                DailyActivityCard(steps = 6200, stepTarget = 10000, calories = 320, calorieTarget = 500)
            }

            Spacer(Modifier.height(16.dp))

            AnimatedVisibility(visible = loaded) {
                WeeklySummaryChart(weekData = listOf(4000, 6000, 7000, 9000, 8000, 5000, 3000))
            }

            Spacer(Modifier.height(16.dp))

            AnimatedVisibility(visible = loaded) {
                WorkoutSummaryCard(workouts = listOf("Morning Yoga", "Evening Run"))
            }

            Spacer(Modifier.height(16.dp))

            AnimatedVisibility(visible = loaded) {
                TipsCard(
                    tips = listOf(
                        "Drink water after every workout 💧",
                        "Stretch before running 🧘",
                        "Try high-protein meals 🍗"
                    )
                )
            }

            Spacer(Modifier.height(80.dp))
        }
    }
}

@Composable
fun ProfileGreeting() {
    val currentHour = LocalTime.now().hour
    val greeting = when (currentHour) {
        in 5..11 -> "Good Morning ☀️"
        in 12..17 -> "Good Afternoon 🌤️"
        else -> "Good Evening 🌙"
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(
            model = "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )
        Spacer(Modifier.width(12.dp))
        Column {
            Text(greeting, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text("Keep moving today!", color = Color.Gray, fontSize = 14.sp)
        }
    }
}

@Composable
fun DailyActivityCard(steps: Int, stepTarget: Int, calories: Int, calorieTarget: Int) {
    val stepProgress by animateFloatAsState(targetValue = min(steps / stepTarget.toFloat(), 1f))
    val calProgress by animateFloatAsState(
        targetValue = min(
            calories / calorieTarget.toFloat(),
            1f
        )
    )

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Daily Activity", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Text("Steps: $steps / $stepTarget")
                Text("Calories: $calories / $calorieTarget kcal")
            }
            Row {
                CircularProgressIndicator(
                    progress = { stepProgress },
                    strokeWidth = 6.dp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(50.dp)
                )
                Spacer(Modifier.width(12.dp))
                LinearProgressIndicator(
                    progress = { calProgress }, // <-- Corrected line
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                )
            }
        }
    }
}

@Composable
fun WeeklySummaryChart(weekData: List<Int>) {
    val max = weekData.maxOrNull()?.toFloat() ?: 1f
    val animatedProgresses = weekData.map { target ->
        animateFloatAsState(target / max).value
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Weekly Summary", fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))
            val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
            days.forEachIndexed { i, day ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Text(day, modifier = Modifier.width(40.dp))
                    LinearProgressIndicator(
                        progress = { animatedProgresses[i] },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )
                }
            }
        }
    }
}

@Composable
fun WorkoutSummaryCard(workouts: List<String>) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Recent Workouts", fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(workouts.size) { i ->
                    AnimatedVisibility(visible = true) {
                        Card(
                            modifier = Modifier
                                .width(140.dp)
                                .height(80.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                        ) {
                            Box(
                                Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(workouts[i], color = MaterialTheme.colorScheme.onPrimaryContainer)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TipsCard(tips: List<String>) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Tips & Suggestions", fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            tips.forEach {
                Text("• $it", fontSize = 14.sp)
                Spacer(Modifier.height(4.dp))
            }
        }
    }
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    NavigationBar {
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Screen.Home.route) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Screen.Logs.route) },
            icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Activity")
            },
            label = { Text("Activity") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Screen.AddLog.route) },
            icon = { Icon(Icons.Default.AddCircle, contentDescription = "Add") },
            label = { Text("Add") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Screen.Tips.route) },
            icon = { Icon(Icons.Default.Lightbulb, contentDescription = "Tips") },
            label = { Text("Tips") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Screen.Profile.route) },
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewHomeScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val appContainer = remember { AppContainer(context.applicationContext as Application) }

    MaterialTheme {
        HomeScreen(navController = navController, appContainer = appContainer)
    }
}


