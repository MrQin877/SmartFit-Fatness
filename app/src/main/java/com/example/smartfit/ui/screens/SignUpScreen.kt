// app/src/main/java/com/example/smartfit/ui/screens/SignUpScreen.kt
package com.example.smartfit.ui.screens

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
import androidx.compose.ui.graphics.Color
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
import com.example.smartfit.ui.navigation.Screen
import com.example.smartfit.ui.theme.RegisterBackground
import androidx.compose.runtime.Composable




@Composable
fun SignUpScreen(
    navController: NavHostController,
    appContainer: AppContainer
) {

    val accent = Color(0xFFAEF200)

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }

    val canRegister = name.isNotBlank() &&
            email.isNotBlank() &&
            password.length >= 6 &&
            password == confirm

    val scroll = rememberScrollState()

        Scaffold(
            containerColor = Color.Transparent,
            contentWindowInsets = WindowInsets(0)   // <- no auto padding around content
        ) { _ ->
            Box(Modifier.fillMaxSize()) {
                RegisterBackground()                // full-bleed bg

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding()
                        .imePadding()
                        .padding(horizontal = 24.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                Spacer(Modifier.height(32.dp))

                Text(
                    "Create",
                    color = Color.White,
                    fontSize = 44.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    "Account",
                    color = Color.White,
                    fontSize = 44.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(Modifier.height(20.dp))

                GlassField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = "Name"
                )
                Spacer(Modifier.height(14.dp))
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
                Spacer(Modifier.height(14.dp))
                GlassField(
                    value = confirm,
                    onValueChange = { confirm = it },
                    placeholder = "Confirm Password",
                    isPassword = true
                )

                Spacer(Modifier.height(22.dp))

                Button(
                    onClick = {
                        // TODO persist user then go to Login or Home
                        navController.navigate(Screen.Login.route) {
                            launchSingleTop = true
                        }
                    },
                    enabled = canRegister,
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
                    Text("Register", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                }

                Spacer(Modifier.height(18.dp))

                val login = buildAnnotatedString {
                    append("Already have an account? ")
                    withStyle(SpanStyle(color = accent, fontWeight = FontWeight.SemiBold)) {
                        append("Login")
                    }
                }
                Text(
                    login,
                    color = Color.White,
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.Login.route)
                    }
                )

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

/** A single-border, glass-like text field (no double outline). */
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
            .background(Color.White.copy(alpha = 0.06f)) // translucent fill
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.22f),   // single subtle stroke
                shape = shape
            )
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

