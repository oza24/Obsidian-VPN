package com.example.vpn_ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.vpn_ui.ui.components.BottomNav
import com.example.vpn_ui.ui.screens.*
import com.example.vpn_ui.ui.theme.VPN_UITheme
import kotlinx.coroutines.runBlocking

class MyApp : android.app.Application() {
    override fun onCreate() {
        super.onCreate()
        com.wireguard.android.backend.GoBackend.setAlwaysOnCallback { }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if already logged in
        val isAlreadyLoggedIn = runBlocking { TokenManager.isLoggedIn(this@MainActivity) }

        setContent {
            VPN_UITheme {
                var authScreen by remember { mutableStateOf("login") }
                var isLoggedIn by remember { mutableStateOf(isAlreadyLoggedIn) }
                var currentScreen by remember { mutableStateOf("home") }

                when {
                    !isLoggedIn -> {
                        when (authScreen) {
                            "login" -> LoginScreen(
                                onLoginSuccess = { isLoggedIn = true },
                                onGoToRegister = { authScreen = "register" }
                            )
                            "register" -> RegisterScreen(
                                onRegisterSuccess = { isLoggedIn = true },
                                onGoToLogin = { authScreen = "login" }
                            )
                        }
                    }
                    else -> {
                        Box(modifier = Modifier.fillMaxSize()) {
                            when (currentScreen) {
                                "home"     -> HomeScreen()
                                "servers"  -> ServerScreen()
                                "activity" -> ActivityScreen()
                                "profile"  -> ProfileScreen(
                                    onLogout = {
                                        isLoggedIn = false
                                        authScreen = "login"
                                        currentScreen = "home"
                                    }
                                )
                            }
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Bottom
                            ) {
                                BottomNav(currentScreen) { currentScreen = it }
                            }
                        }
                    }
                }
            }
        }
    }
}
