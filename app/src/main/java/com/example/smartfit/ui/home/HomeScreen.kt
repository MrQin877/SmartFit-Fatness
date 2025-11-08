package com.example.smartfit.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.smartfit.di.AppGraph
import com.example.smartfit.ui.navigation.Dest
import kotlinx.coroutines.delay
import java.time.LocalTime
import kotlin.math.min
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartfit.ui.AppViewModelProvider
import com.example.smartfit.ui.home.HomeViewModel


@Composable
fun HomeScreen(
    navController: NavHostController,
    contentPadding: PaddingValues = PaddingValues(0.dp))
{
    val vm: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val ui = vm.ui.collectAsState().value

    var loaded by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(300)
        loaded = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.height(8.dp))

        AnimatedVisibility(visible = loaded) { ProfileGreeting() }

        Spacer(Modifier.height(16.dp))

        AnimatedVisibility(visible = loaded) {
            DailyActivityCard(
                steps = ui.stepsToday,
                stepTarget = 16000,
                calories = ui.caloriesToday,
                calorieTarget = 680
            )
        }

        Spacer(Modifier.height(16.dp))

        AnimatedVisibility(visible = loaded) {
            WorkoutSummaryCard(workouts = ui.workouts)
        }

        Spacer(Modifier.height(16.dp))

        AnimatedVisibility(visible = loaded) {
            TipsCard(
                tips = listOf(
                    "Drink water after every workout 💧",
                    "Stretch before running 🧘",
                    "Try high-protein meals 🍗"
                ),
                onViewAll = { navController.navigate(Dest.Tips) }
            )
        }

        Spacer(Modifier.height(80.dp))
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
fun DailyActivityCard(
    steps: Int,
    stepTarget: Int,
    calories: Int,
    calorieTarget: Int
) {
    val stepProgress by animateFloatAsState(targetValue = min(steps / stepTarget.toFloat(), 1f))
    val calProgress by animateFloatAsState(targetValue = min(calories / calorieTarget.toFloat(), 1f))

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Daily Activity",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Steps
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            progress = { stepProgress },
                            strokeWidth = 8.dp,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(80.dp)
                        )
                        Text(
                            text = "${(stepProgress * 100).toInt()}%",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(Modifier.height(6.dp))
                    Text("Steps", fontWeight = FontWeight.SemiBold)
                    Text("$steps / $stepTarget", fontSize = 12.sp, color = Color.Gray)
                }

                // Calories
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            progress = { calProgress },
                            strokeWidth = 8.dp,
                            color = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.size(80.dp)
                        )
                        Text(
                            text = "${(calProgress * 100).toInt()}%",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }
                    Spacer(Modifier.height(6.dp))
                    Text("Calories", fontWeight = FontWeight.SemiBold)
                    Text("$calories / $calorieTarget kcal", fontSize = 12.sp, color = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun WorkoutSummaryCard(workouts: List<String>, onViewAll: (() -> Unit)? = null) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Recent Workouts", fontWeight = FontWeight.Bold)
                TextButton(onClick = { onViewAll?.invoke() }) {
                    Text("View all →", color = MaterialTheme.colorScheme.primary)
                }
            }

            Spacer(Modifier.height(8.dp))

            workouts.forEach { workout ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(workout, color = MaterialTheme.colorScheme.secondary)
                    }
                }
            }
        }
    }
}

@Composable
fun TipsCard(tips: List<String>, onViewAll: (() -> Unit)? = null) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Tips & Suggestions", fontWeight = FontWeight.Bold)
                TextButton(onClick = { onViewAll?.invoke() }) {
                    Text("View all →", color = MaterialTheme.colorScheme.primary)
                }
            }

            Spacer(Modifier.height(8.dp))

            tips.forEach {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Text(
                        text = "• $it",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(12.dp),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}

/* ─────────────────────────────────────────────────────────────
   Optional: keep only if you want a simple bottom bar here.
   Otherwise, delete this whole composable (you use a pill bar).
   ───────────────────────────────────────────────────────────── */
@Composable
fun BottomNavBar(navController: NavHostController) {
    NavigationBar {
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Dest.Home) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Dest.Logs) },
            icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Activity") },
            label = { Text("Activity") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Dest.Tips) },
            icon = { Icon(Icons.Default.Lightbulb, contentDescription = "Tips") },
            label = { Text("Tips") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Dest.Profile) },
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") }
        )
    }
}

/* Preview */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewHomeScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val graph = remember { AppGraph(context) }

    MaterialTheme {
        HomeScreen(
            navController = navController,
            contentPadding = PaddingValues(0.dp)
        )
    }
}
