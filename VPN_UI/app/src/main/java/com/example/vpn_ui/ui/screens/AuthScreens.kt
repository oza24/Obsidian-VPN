package com.example.vpn_ui.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vpn_ui.LoginRequest
import com.example.vpn_ui.RegisterRequest
import com.example.vpn_ui.RetrofitClient
import com.example.vpn_ui.TokenManager
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, onGoToRegister: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf("") }
    val bg = Brush.verticalGradient(listOf(Color(0xFF020B0F), Color(0xFF041A1F)))

    Box(modifier = Modifier.fillMaxSize().background(bg), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(60.dp))
            Text("⏻", fontSize = 48.sp)
            Spacer(Modifier.height(8.dp))
            Text("OBSIDIAN VPN", color = Color(0xFF00E5A8), fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text("Secure. Private. Fast.", color = Color.Gray, fontSize = 13.sp)
            Spacer(Modifier.height(48.dp))

            Box(modifier = Modifier.fillMaxWidth().background(Color(0xFF1A1F24), RoundedCornerShape(20.dp)).padding(24.dp)) {
                Column {
                    Text("Welcome Back", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text("Sign in to your account", color = Color.Gray, fontSize = 13.sp)
                    Spacer(Modifier.height(24.dp))
                    VpnTextField(value = email, onValueChange = { email = it; errorMsg = "" }, label = "Email", keyboardType = KeyboardType.Email)
                    Spacer(Modifier.height(12.dp))
                    VpnTextField(value = password, onValueChange = { password = it; errorMsg = "" }, label = "Password",
                        keyboardType = KeyboardType.Password, isPassword = true, passwordVisible = passwordVisible,
                        onPasswordToggle = { passwordVisible = !passwordVisible })
                    if (errorMsg.isNotEmpty()) { Spacer(Modifier.height(8.dp)); Text(errorMsg, color = Color.Red, fontSize = 12.sp) }
                    Spacer(Modifier.height(24.dp))
                    Button(
                        onClick = {
                            if (email.isBlank() || password.isBlank()) { errorMsg = "Please fill in all fields"; return@Button }
                            scope.launch {
                                isLoading = true; errorMsg = ""
                                try {
                                    val response = RetrofitClient.api.login(LoginRequest(email.trim(), password))
                                    TokenManager.saveToken(context, response.token)
                                    TokenManager.saveUser(context, response.user.name, response.user.email)
                                    onLoginSuccess()
                                } catch (e: Exception) { errorMsg = "Invalid email or password" }
                                finally { isLoading = false }
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00E5A8)),
                        shape = RoundedCornerShape(12.dp), enabled = !isLoading
                    ) {
                        if (isLoading) CircularProgressIndicator(color = Color.Black, modifier = Modifier.size(20.dp))
                        else Text("SIGN IN", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
            Row {
                Text("Don't have an account? ", color = Color.Gray, fontSize = 14.sp)
                Text("Sign Up", color = Color(0xFF00E5A8), fontSize = 14.sp, fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onGoToRegister() })
            }
            Spacer(Modifier.height(40.dp))
        }
    }
}

@Composable
fun RegisterScreen(onRegisterSuccess: () -> Unit, onGoToLogin: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf("") }
    val bg = Brush.verticalGradient(listOf(Color(0xFF020B0F), Color(0xFF041A1F)))

    Box(modifier = Modifier.fillMaxSize().background(bg), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(60.dp))
            Text("⏻", fontSize = 48.sp)
            Spacer(Modifier.height(8.dp))
            Text("OBSIDIAN VPN", color = Color(0xFF00E5A8), fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text("Create your account", color = Color.Gray, fontSize = 13.sp)
            Spacer(Modifier.height(48.dp))

            Box(modifier = Modifier.fillMaxWidth().background(Color(0xFF1A1F24), RoundedCornerShape(20.dp)).padding(24.dp)) {
                Column {
                    Text("Create Account", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text("Join Obsidian VPN today", color = Color.Gray, fontSize = 13.sp)
                    Spacer(Modifier.height(24.dp))
                    VpnTextField(value = name, onValueChange = { name = it; errorMsg = "" }, label = "Full Name")
                    Spacer(Modifier.height(12.dp))
                    VpnTextField(value = email, onValueChange = { email = it; errorMsg = "" }, label = "Email", keyboardType = KeyboardType.Email)
                    Spacer(Modifier.height(12.dp))
                    VpnTextField(value = password, onValueChange = { password = it; errorMsg = "" }, label = "Password",
                        keyboardType = KeyboardType.Password, isPassword = true, passwordVisible = passwordVisible,
                        onPasswordToggle = { passwordVisible = !passwordVisible })
                    Spacer(Modifier.height(12.dp))
                    VpnTextField(value = confirmPassword, onValueChange = { confirmPassword = it; errorMsg = "" }, label = "Confirm Password",
                        keyboardType = KeyboardType.Password, isPassword = true, passwordVisible = passwordVisible,
                        onPasswordToggle = { passwordVisible = !passwordVisible })
                    if (errorMsg.isNotEmpty()) { Spacer(Modifier.height(8.dp)); Text(errorMsg, color = Color.Red, fontSize = 12.sp) }
                    Spacer(Modifier.height(24.dp))
                    Button(
                        onClick = {
                            when {
                                name.isBlank() || email.isBlank() || password.isBlank() -> errorMsg = "Please fill in all fields"
                                password != confirmPassword -> errorMsg = "Passwords do not match"
                                password.length < 6 -> errorMsg = "Password must be at least 6 characters"
                                else -> scope.launch {
                                    isLoading = true; errorMsg = ""
                                    try {
                                        val response = RetrofitClient.api.register(RegisterRequest(name.trim(), email.trim(), password))
                                        TokenManager.saveToken(context, response.token)
                                        TokenManager.saveUser(context, response.user.name, response.user.email)
                                        onRegisterSuccess()
                                    } catch (e: Exception) { errorMsg = "Registration failed. Email may already exist." }
                                    finally { isLoading = false }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00E5A8)),
                        shape = RoundedCornerShape(12.dp), enabled = !isLoading
                    ) {
                        if (isLoading) CircularProgressIndicator(color = Color.Black, modifier = Modifier.size(20.dp))
                        else Text("CREATE ACCOUNT", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
            Row {
                Text("Already have an account? ", color = Color.Gray, fontSize = 14.sp)
                Text("Sign In", color = Color(0xFF00E5A8), fontSize = 14.sp, fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onGoToLogin() })
            }
            Spacer(Modifier.height(40.dp))
        }
    }
}

@Composable
fun VpnTextField(
    value: String, onValueChange: (String) -> Unit, label: String,
    keyboardType: KeyboardType = KeyboardType.Text, isPassword: Boolean = false,
    passwordVisible: Boolean = false, onPasswordToggle: (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value, onValueChange = onValueChange,
        label = { Text(label, color = Color.Gray) }, singleLine = true,
        modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = if (isPassword && onPasswordToggle != null) {
            { Text(if (passwordVisible) "🙈" else "👁", modifier = Modifier.clickable { onPasswordToggle() }, fontSize = 16.sp) }
        } else null,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White, unfocusedTextColor = Color.White,
            focusedBorderColor = Color(0xFF00E5A8), unfocusedBorderColor = Color(0xFF2A2F35),
            cursorColor = Color(0xFF00E5A8)
        ), shape = RoundedCornerShape(12.dp)
    )
}
