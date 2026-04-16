//package com.example.vpn_ui.ui.screens
//
//import android.app.Activity
//import android.net.VpnService
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.*
//import androidx.compose.ui.draw.blur
//import androidx.compose.ui.draw.shadow
//import androidx.compose.ui.graphics.*
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.vpn_ui.SelectedServer
//import com.example.vpn_ui.VpnManager
//import kotlinx.coroutines.launch
//
//@Composable
//fun HomeScreen() {
//
//    val context = LocalContext.current
//    val vpnManager = remember { VpnManager(context) }
//    var connected by remember { mutableStateOf(false) }
//    val scope = rememberCoroutineScope()
//
//    // ← REPLACE startActivityForResult WITH THIS
//    val vpnPermissionLauncher = rememberLauncherForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ) { result ->
//        if (result.resultCode == Activity.RESULT_OK) {
//            scope.launch {
//                val server = SelectedServer.current ?: return@launch
//                vpnManager.connect(server)
//                connected = true
//            }
//        }
//    }
//
//    val bg = Brush.verticalGradient(
//        listOf(Color(0xFF020B0F), Color(0xFF041A1F))
//    )
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(bg)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .verticalScroll(rememberScrollState())
//                .padding(20.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text("OBSIDIAN VPN", color = Color(0xFF00E5A8), fontSize = 18.sp, fontWeight = FontWeight.Bold)
//                Text("⚙️", fontSize = 20.sp)
//            }
//
//            Spacer(Modifier.height(30.dp))
//
//            Text("CURRENT STATUS", color = Color.Gray, fontSize = 12.sp)
//            Spacer(Modifier.height(6.dp))
//
//            // ← STATUS TEXT NOW REFLECTS REAL STATE
//            Text(
//                if (connected) "Connected" else "Not Connected",
//                color = Color.White,
//                fontSize = 28.sp,
//                fontWeight = FontWeight.Bold
//            )
//
//            Spacer(Modifier.height(8.dp))
//
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Box(
//                    modifier = Modifier
//                        .size(8.dp)
//                        .background(
//                            if (connected) Color(0xFF00E5A8) else Color.Red,  // ← GREEN WHEN CONNECTED
//                            CircleShape
//                        )
//                )
//                Spacer(Modifier.width(6.dp))
//                Text(
//                    if (connected) "Your IP is hidden" else "Your IP is visible",
//                    color = Color.Gray
//                )
//            }
//
//            Spacer(Modifier.height(40.dp))
//
//            Box(contentAlignment = Alignment.Center) {
//
//                Box(
//                    modifier = Modifier
//                        .size(260.dp)
//                        .blur(80.dp)
//                        .background(Color(0xFF00E5A8), CircleShape)
//                )
//
//                Box(
//                    modifier = Modifier
//                        .size(200.dp)
//                        .shadow(20.dp, CircleShape)
//                        .background(
//                            Brush.radialGradient(
//                                listOf(Color(0xFF1A2E2A), Color(0xFF0B1F1B))
//                            ),
//                            CircleShape
//                        )
//                        .clickable(
//                            indication = null,
//                            interactionSource = remember { MutableInteractionSource() }
//                        ) {
//                            scope.launch {
//                                // ← CLEAN BUTTON LOGIC
//                                val intent = VpnService.prepare(context)
//                                if (intent != null) {
//                                    vpnPermissionLauncher.launch(intent)
//                                    return@launch
//                                }
//
//                                if (!connected) {
//                                    val server = SelectedServer.current ?: return@launch
//                                    vpnManager.connect(server)
//                                    connected = true
//                                } else {
//                                    vpnManager.disconnect()
//                                    connected = false
//                                }
//                            }
//                        },
//                    contentAlignment = Alignment.Center
//                ) {
//                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                        Text("⏻", fontSize = 36.sp, color = Color(0xFF00E5A8))
//                        Spacer(Modifier.height(8.dp))
//                        Text(
//                            if (connected) "DISCONNECT" else "CONNECT",  // ← DYNAMIC LABEL
//                            color = Color(0xFF00E5A8),
//                            fontWeight = FontWeight.Bold
//                        )
//                    }
//                }
//            }
//
//            Spacer(Modifier.height(40.dp))
//
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(Color(0xFF1A1F24).copy(alpha = 0.6f), RoundedCornerShape(20.dp))
//                    .padding(16.dp)
//            ) {
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Box(
//                        modifier = Modifier.size(50.dp).background(Color.Black, CircleShape),
//                        contentAlignment = Alignment.Center
//                    ) { Text("🇺🇸") }
//                    Spacer(Modifier.width(12.dp))
//                    Column {
//                        Text("FASTEST SERVER", color = Color.Gray, fontSize = 12.sp)
//                        Text("United States, NY", color = Color.White, fontWeight = FontWeight.Bold)
//                    }
//                    Spacer(Modifier.weight(1f))
//                    Column(horizontalAlignment = Alignment.End) {
//                        Text("24ms", color = Color(0xFF00E5A8))
//                        Text("Latency", color = Color.Gray, fontSize = 12.sp)
//                    }
//                }
//            }
//
//            Spacer(Modifier.height(20.dp))
//
//            Row {
//                Box(
//                    modifier = Modifier
//                        .weight(1f)
//                        .background(Color(0xFF1A1F24).copy(0.6f), RoundedCornerShape(16.dp))
//                        .padding(16.dp)
//                ) {
//                    Column {
//                        Text("PROTOCOL", color = Color.Gray, fontSize = 12.sp)
//                        Text("WireGuard®", color = Color.White)
//                    }
//                }
//                Spacer(Modifier.width(10.dp))
//                Box(
//                    modifier = Modifier
//                        .weight(1f)
//                        .background(Color(0xFF1A1F24).copy(0.6f), RoundedCornerShape(16.dp))
//                        .padding(16.dp)
//                ) {
//                    Column {
//                        Text("ENCRYPTION", color = Color.Gray, fontSize = 12.sp)
//                        Text("AES-256", color = Color.White)
//                    }
//                }
//            }
//        }
//    }
//}


package com.example.vpn_ui.ui.screens

import android.app.Activity
import android.net.VpnService
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vpn_ui.EndSessionRequest
import com.example.vpn_ui.RetrofitClient
import com.example.vpn_ui.SelectedServer
import com.example.vpn_ui.StartSessionRequest
import com.example.vpn_ui.TokenManager
import com.example.vpn_ui.VpnManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val vpnManager = remember { VpnManager(context) }
    val scope = rememberCoroutineScope()

    var connected by remember { mutableStateOf(false) }
    var virtualIp by remember { mutableStateOf("") }
    var sessionSeconds by remember { mutableStateOf(0) }
    var isLoading by remember { mutableStateOf(false) }

    // Live session timer
    LaunchedEffect(connected) {
        if (connected) {
            while (true) { delay(1000); sessionSeconds++ }
        } else {
            sessionSeconds = 0
        }
    }

    // Check if already connected on app open
    LaunchedEffect(Unit) {
        try {
            val token = RetrofitClient.getAuthHeader(context)
            val result = RetrofitClient.api.getCurrentSession(token)
            if (result.active && result.session != null) {
                connected = true
                virtualIp = result.session.virtualIp
                sessionSeconds = result.session.liveDuration
            }
        } catch (e: Exception) { /* no active session */ }
    }

    // VPN permission launcher
    val vpnPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            scope.launch {
                doConnect(context, vpnManager,
                    onConnected = { ip -> connected = true; virtualIp = ip; isLoading = false },
                    onError = { isLoading = false }
                )
            }
        }
    }

    // Format hh:mm:ss
    val formattedTime = remember(sessionSeconds) {
        "%02d:%02d:%02d".format(sessionSeconds / 3600, (sessionSeconds % 3600) / 60, sessionSeconds % 60)
    }

    val bg = Brush.verticalGradient(listOf(Color(0xFF020B0F), Color(0xFF041A1F)))

    Box(modifier = Modifier.fillMaxSize().background(bg)) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("OBSIDIAN VPN", color = Color(0xFF00E5A8), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text("⚙️", fontSize = 20.sp)
            }

            Spacer(Modifier.height(30.dp))

            // Status
            Text("CURRENT STATUS", color = Color.Gray, fontSize = 12.sp)
            Spacer(Modifier.height(6.dp))
            Text(
                when { isLoading -> "Connecting..."; connected -> "Connected"; else -> "Not Connected" },
                color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(8.dp).background(if (connected) Color(0xFF00E5A8) else Color.Red, CircleShape))
                Spacer(Modifier.width(6.dp))
                Text(if (connected) "Your IP is hidden" else "Your IP is visible", color = Color.Gray, fontSize = 13.sp)
            }

            // Session timer
            if (connected) {
                Spacer(Modifier.height(8.dp))
                Text(formattedTime, color = Color(0xFF00E5A8), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                if (virtualIp.isNotEmpty()) {
                    Text("Virtual IP: $virtualIp", color = Color.Gray, fontSize = 12.sp)
                }
            }

            Spacer(Modifier.height(40.dp))

            // Power button
            Box(contentAlignment = Alignment.Center) {
                Box(modifier = Modifier.size(260.dp).blur(80.dp).background(Color(0xFF00E5A8), CircleShape))
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .shadow(20.dp, CircleShape)
                        .background(Brush.radialGradient(listOf(Color(0xFF1A2E2A), Color(0xFF0B1F1B))), CircleShape)
                        .clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) {
                            if (isLoading) return@clickable
                            scope.launch {
                                if (!connected) {
                                    val server = SelectedServer.current
                                    if (server == null) {
                                        Toast.makeText(context, "Please select a server first!", Toast.LENGTH_SHORT).show()
                                        return@launch
                                    }
                                    isLoading = true
                                    val intent = VpnService.prepare(context)
                                    if (intent != null) { vpnPermissionLauncher.launch(intent); return@launch }
                                    doConnect(context, vpnManager,
                                        onConnected = { ip -> connected = true; virtualIp = ip; isLoading = false },
                                        onError = { isLoading = false }
                                    )
                                } else {
                                    isLoading = true
                                    try {
                                        vpnManager.disconnect()
                                        val token = RetrofitClient.getAuthHeader(context)
                                        RetrofitClient.api.endSession(token, EndSessionRequest())
                                        connected = false; virtualIp = ""
                                    } catch (e: Exception) { e.printStackTrace() }
                                    finally { isLoading = false }
                                }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (isLoading) CircularProgressIndicator(color = Color(0xFF00E5A8), modifier = Modifier.size(36.dp))
                        else Text("⏻", fontSize = 36.sp, color = Color(0xFF00E5A8))
                        Spacer(Modifier.height(8.dp))
                        Text(if (connected) "DISCONNECT" else "CONNECT", color = Color(0xFF00E5A8), fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(Modifier.height(40.dp))

            // Server card
            Box(
                modifier = Modifier.fillMaxWidth()
                    .background(Color(0xFF1A1F24).copy(alpha = 0.6f), RoundedCornerShape(20.dp)).padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(50.dp).background(Color.Black, CircleShape), contentAlignment = Alignment.Center) {
                        Text(when (SelectedServer.current?.country) { "India" -> "🇮🇳"; "Singapore" -> "🇸🇬"; else -> "🌍" })
                    }
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(if (connected) "CONNECTED SERVER" else "SELECTED SERVER", color = Color.Gray, fontSize = 12.sp)
                        Text(SelectedServer.current?.let { "${it.country}, ${it.city}" } ?: "No server selected",
                            color = Color.White, fontWeight = FontWeight.Bold)
                    }
                    Spacer(Modifier.weight(1f))
                    Column(horizontalAlignment = Alignment.End) {
                        Text(SelectedServer.current?.let { "${it.ping}ms" } ?: "--", color = Color(0xFF00E5A8))
                        Text("Latency", color = Color.Gray, fontSize = 12.sp)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            Row {
                Box(modifier = Modifier.weight(1f).background(Color(0xFF1A1F24).copy(0.6f), RoundedCornerShape(16.dp)).padding(16.dp)) {
                    Column { Text("PROTOCOL", color = Color.Gray, fontSize = 12.sp); Text("WireGuard®", color = Color.White) }
                }
                Spacer(Modifier.width(10.dp))
                Box(modifier = Modifier.weight(1f).background(Color(0xFF1A1F24).copy(0.6f), RoundedCornerShape(16.dp)).padding(16.dp)) {
                    Column { Text("ENCRYPTION", color = Color.Gray, fontSize = 12.sp); Text("AES-256", color = Color.White) }
                }
            }
            Spacer(Modifier.height(80.dp))
        }
    }
}

private suspend fun doConnect(
    context: android.content.Context,
    vpnManager: VpnManager,
    onConnected: (virtualIp: String) -> Unit,
    onError: () -> Unit
) {
    try {
        val server = SelectedServer.current ?: run { onError(); return }
        vpnManager.connect(server)
        val token = RetrofitClient.getAuthHeader(context)
        RetrofitClient.api.startSession(token, StartSessionRequest(serverId = server._id ?: ""))
        onConnected("10.0.1.3")
    } catch (e: Exception) {
        e.printStackTrace()
        onError()
    }
}
