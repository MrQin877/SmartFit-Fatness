package com.example.smartfit.ui.screens

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color      // <-- keep Compose Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.smartfit.AppContainer
import com.example.smartfit.data.datastore.UserPreferences
import com.example.smartfit.ui.navigation.Screen
import com.example.smartfit.ui.theme.LoginBackground
import kotlinx.coroutines.launch
import android.graphics.Color as AColor       // <-- alias Android Color only

@Composable
fun LoginScreen(
    navController: NavHostController,
    appContainer: AppContainer
) {

    val accent = Color(0xFFAEF200)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(false) }

    val ctx = androidx.compose.ui.platform.LocalContext.current
    val scroll = rememberScrollState()
    val scope = rememberCoroutineScope()     // <-- get a UI scope

    val canLogin = email.isNotBlank() && password.isNotBlank() && !loading

    Scaffold(
        containerColor = Color.Transparent,
        contentWindowInsets = WindowInsets(0)
    ) { _ ->
        Box(Modifier.fillMaxSize()) {
            LoginBackground()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding()
                    .imePadding()
                    .padding(horizontal = 24.dp)
                    .verticalScroll(scroll),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(36.dp))

                Text("Welcome", color = Color.White, fontSize = 44.sp, fontWeight = FontWeight.ExtraBold)
                Text("Back", color = Color.White, fontSize = 44.sp, fontWeight = FontWeight.ExtraBold)

                Spacer(Modifier.height(24.dp))

                GlassField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "Email",
                    keyboardType = KeyboardType.Email
                )
                Spacer(Modifier.height(14.dp))

                GlassField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "Password",
                    isPassword = true
                )

                Spacer(Modifier.height(22.dp))

                Button(
                    onClick = {
                        if (loading) return@Button
                        scope.launch {
                            loading = true
                            error = null
                            val ok = appContainer.userRepository.login(email, password) != null
                            if (ok) {
                                UserPreferences.setLoggedIn(ctx, true)
                                navController.navigate(Screen.Home.route) {
                                    popUpTo(Screen.Login.route) { inclusive = true }
                                    launchSingleTop = true
                                }
                            } else {
                                error = "Invalid email or password"
                            }
                            loading = false
                        }
                    },
                    enabled = canLogin,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = accent,
                        contentColor = Color.Black,
                        disabledContainerColor = accent.copy(alpha = 0.5f),
                        disabledContentColor = Color.Black.copy(alpha = 0.6f)
                    )
                ) {
                    Text(if (loading) "Signing in..." else "Login",
                        fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                }

                if (error != null) {
                    Spacer(Modifier.height(10.dp))
                    Text(error!!, color = MaterialTheme.colorScheme.error)
                }

                Spacer(Modifier.height(18.dp))

                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Divider(Modifier.weight(1f), color = Color.White.copy(alpha = 0.24f))
                    Text("  OR  ", color = Color.White.copy(alpha = 0.85f),
                        style = MaterialTheme.typography.labelLarge)
                    Divider(Modifier.weight(1f), color = Color.White.copy(alpha = 0.24f))
                }

                Spacer(Modifier.height(14.dp))

                val register = buildAnnotatedString {
                    append("Don’t have an account? ")
                    withStyle(SpanStyle(color = accent, fontWeight = FontWeight.SemiBold)) {
                        append("Register")
                    }
                }
                Text(register, color = Color.White,
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.SignUp.route) { launchSingleTop = true }
                    })

                Spacer(Modifier.height(32.dp))
            }
        }
    }
}

/** Glass field like Sign-Up */
@Composable
private fun GlassField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false
) {
    val shape = RoundedCornerShape(24.dp)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
            .clip(shape)
            .background(Color.White.copy(alpha = 0.06f))
            .border(1.dp, Color.White.copy(alpha = 0.22f), shape)
            .padding(horizontal = 14.dp)
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            placeholder = { Text(placeholder, color = Color.White.copy(alpha = 0.65f)) },
            textStyle = LocalTextStyle.current.copy(color = Color.White, fontSize = 18.sp),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                cursorColor = Color.White
            ),
            shape = shape,
            modifier = Modifier.fillMaxSize()
        )
    }
}


