// app/src/main/java/com/example/smartfit/ui/auth/OnboardingScreen.kt
package com.example.smartfit.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.smartfit.R    // <-- use your app's R, not android.R
import com.example.smartfit.ui.AppViewModelProvider
import com.example.smartfit.ui.navigation.Dest

@Composable
fun OnboardingScreen(
    navController: NavHostController
) {
    val vm: OnboardingViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val ui = vm.state.collectAsState().value

    // one-shot nav when finished
    LaunchedEffect(Unit) {
        vm.effect.collect { eff ->
            if (eff is OnboardingEffect.Finished) {
                navController.navigate(Dest.Login) {
                    popUpTo(Dest.Onboarding) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }
    }

    when (ui.step) {
        1 -> StepWelcome(
            onNext = vm::next,
            onSkip = vm::skip
        )
        2 -> StepGoal(
            goal = ui.goal,
            onGoalChange = { vm.setGoal(it) },
            onContinue = { vm.finish(saveGoal = true) },
            onSkip = vm::skip
        )
    }
}

@Composable
private fun StepWelcome(
    onNext: () -> Unit,
    onSkip: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Replace with your own illustration
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "Welcome",
            modifier = Modifier.size(160.dp)
        )
        Spacer(Modifier.height(24.dp))
        Text("Welcome to SmartFit!", style = MaterialTheme.typography.headlineSmall, fontSize = 28.sp)
        Spacer(Modifier.height(12.dp))
        Text(
            "Your personal fitness tracker to help you stay healthy and motivated every day.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 20.sp,
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(32.dp))
        Button(onClick = onNext, modifier = Modifier.fillMaxWidth()) { Text("Next") }
        Spacer(Modifier.height(8.dp))
        TextButton(onClick = onSkip) { Text("Skip for now") }
    }
}

@Composable
private fun StepGoal(
    goal: Int,
    onGoalChange: (Int) -> Unit,
    onContinue: () -> Unit,
    onSkip: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Set Your Daily Step Goal", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        Text("Choose your daily target to stay motivated:")
        Spacer(Modifier.height(12.dp))

        Slider(
            value = goal.toFloat(),
            onValueChange = { onGoalChange(it.toInt()) },
            valueRange = 1000f..20000f,
            steps = 9,
            modifier = Modifier.fillMaxWidth()
        )
        Text("${goal} steps", style = MaterialTheme.typography.bodyLarge)

        Spacer(Modifier.height(24.dp))

        Button(onClick = onContinue, modifier = Modifier.fillMaxWidth()) { Text("Continue") }
        Spacer(Modifier.height(8.dp))
        TextButton(onClick = onSkip) { Text("Skip for now") }
    }
}
