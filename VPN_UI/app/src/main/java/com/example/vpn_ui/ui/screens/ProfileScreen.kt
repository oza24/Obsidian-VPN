

package com.example.vpn_ui.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vpn_ui.RetrofitClient
import com.example.vpn_ui.TokenManager
import com.example.vpn_ui.UserModel
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(onLogout: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var user by remember { mutableStateOf<UserModel?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var showLogoutDialog by remember { mutableStateOf(false) }

    // Load real user data from API
    LaunchedEffect(Unit) {
        try {
            val token = RetrofitClient.getAuthHeader(context)
            user = RetrofitClient.api.getProfile(token)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            isLoading = false
        }
    }

    val bg = Brush.verticalGradient(listOf(Color(0xFF020B0F), Color(0xFF041A1F)))

    // Logout confirmation dialog
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            containerColor = Color(0xFF1A1F24),
            title = { Text("Logout", color = Color.White) },
            text = { Text("Are you sure you want to logout?", color = Color.Gray) },
            confirmButton = {
                TextButton(onClick = {
                    scope.launch {
                        TokenManager.clearAll(context)
                        onLogout()
                    }
                }) { Text("Logout", color = Color.Red) }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancel", color = Color(0xFF00E5A8))
                }
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize().background(bg)
            .verticalScroll(rememberScrollState()).padding(16.dp)
    ) {
        // Header
        Text("OBSIDIAN VPN", color = Color(0xFF00E5A8), fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(24.dp))

        if (isLoading) {
            Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFF00E5A8))
            }
        } else {
            // Avatar + name
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier.size(72.dp).background(Color(0xFF2A2F35), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            user?.name?.firstOrNull()?.uppercaseChar()?.toString() ?: "?",
                            color = Color(0xFF00E5A8), fontSize = 28.sp, fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(Modifier.width(16.dp))
                    Column {
                        Text(user?.name ?: "Unknown", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text(user?.email ?: "", color = Color.Gray, fontSize = 13.sp)
                        Spacer(Modifier.height(6.dp))
                        Box(
                            modifier = Modifier.background(
                                if (user?.subscription?.plan == "premium") Color(0xFF00E5A8).copy(0.2f)
                                else Color(0xFF2A2F35), RoundedCornerShape(20.dp)
                            ).padding(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Text(
                                if (user?.subscription?.plan == "premium") "● PREMIUM MEMBER" else "● FREE PLAN",
                                color = if (user?.subscription?.plan == "premium") Color(0xFF00E5A8) else Color.Gray,
                                fontSize = 11.sp, fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // Stats cards
            Row {
                Box(
                    modifier = Modifier.weight(1f).background(Color(0xFF1A1F24).copy(0.6f), RoundedCornerShape(16.dp)).padding(16.dp)
                ) {
                    Column {
                        Text("ACTIVE SINCE", color = Color.Gray, fontSize = 11.sp)
                        Text(
                            user?.subscription?.activeSince?.take(10) ?: "--",
                            color = Color.White, fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(Modifier.width(10.dp))
                Box(
                    modifier = Modifier.weight(1f).background(Color(0xFF1A1F24).copy(0.6f), RoundedCornerShape(16.dp)).padding(16.dp)
                ) {
                    Column {
                        Text("DEVICES", color = Color.Gray, fontSize = 11.sp)
                        Text("${user?.devices ?: 1} / ${user?.maxDevices ?: 10}",
                            color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // Account management section
            SectionHeader("ACCOUNT MANAGEMENT")
            Spacer(Modifier.height(8.dp))
            ProfileMenuItem(title = "Personal Information", subtitle = user?.email ?: "")
            Spacer(Modifier.height(8.dp))
            ProfileMenuItem(title = "Subscription & Billing",
                subtitle = if (user?.subscription?.plan == "premium") "Premium Plan" else "Free Plan")

            Spacer(Modifier.height(20.dp))

            // Security section
            SectionHeader("SECURITY & SYSTEM")
            Spacer(Modifier.height(8.dp))
            ProfileMenuItem(title = "Privacy & Security", badge = "STRICT")
            Spacer(Modifier.height(8.dp))
            ProfileMenuItem(title = "App Settings")

            Spacer(Modifier.height(20.dp))

            // Logout button
            Box(
                modifier = Modifier.fillMaxWidth()
                    .background(Color(0xFF1A1F24).copy(0.6f), RoundedCornerShape(16.dp))
                    .clickable { showLogoutDialog = true }
                    .padding(16.dp)
            ) {
                Text("Logout", color = Color.Red, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(Modifier.height(80.dp))
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(title, color = Color.Gray, fontSize = 11.sp, fontWeight = FontWeight.Bold)
}

@Composable
fun ProfileMenuItem(title: String, subtitle: String = "", badge: String = "") {
    Box(
        modifier = Modifier.fillMaxWidth()
            .background(Color(0xFF1A1F24).copy(0.6f), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(title, color = Color.White)
                    if (badge.isNotEmpty()) {
                        Spacer(Modifier.width(8.dp))
                        Box(
                            modifier = Modifier.background(Color(0xFF00E5A8).copy(0.15f), RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(badge, color = Color(0xFF00E5A8), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                if (subtitle.isNotEmpty()) {
                    Text(subtitle, color = Color.Gray, fontSize = 12.sp)
                }
            }
            Text(">", color = Color.Gray)
        }
    }
}

