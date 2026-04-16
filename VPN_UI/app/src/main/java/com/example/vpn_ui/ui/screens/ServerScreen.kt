//package com.example.vpn_ui.ui.screens
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.*
//import androidx.compose.ui.graphics.*
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.vpn_ui.ui.components.ServerItem
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//
//// 🔥 NEW IMPORTS
//import kotlinx.coroutines.launch
//import com.example.vpn_ui.RetrofitClient
//import com.example.vpn_ui.ServerModel
//import com.example.vpn_ui.SelectedServer
//
//@Composable
//fun ServerScreen() {
//
//    val bg = Brush.verticalGradient(
//        listOf(Color(0xFF020B0F), Color(0xFF041A1F))
//    )
//
//    // 🔥 STATE
//    var servers by remember { mutableStateOf<List<ServerModel>>(emptyList()) }
//
//    // 🔥 API CALL
//    LaunchedEffect(Unit) {
//        try {
//            val result = RetrofitClient.api.getServers()
//
//            println("SERVERS FROM API: $result")   // 🔥 ADD THIS LINE
//
//            servers = result
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(bg)
//            .verticalScroll(rememberScrollState())
//            .padding(16.dp)
//    ) {
//
//        // 🔝 HEADER
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Text(
//                "SELECT SERVER",
//                color = Color.White,
//                fontSize = 20.sp,
//                fontWeight = FontWeight.Bold
//            )
//            Text("⚙️", color = Color.White)
//        }
//
//        Spacer(Modifier.height(20.dp))
//
//        // 🔍 SEARCH BAR
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(
//                    Color(0xFF1A1F24),
//                    RoundedCornerShape(12.dp)
//                )
//                .padding(14.dp)
//        ) {
//            Text("Search for countries...", color = Color.Gray)
//        }
//
//        Spacer(Modifier.height(20.dp))
//
//        // ⚡ FASTEST SERVER (optional logic later)
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(
//                    Brush.horizontalGradient(
//                        listOf(
//                            Color(0xFF00E5A8).copy(0.3f),
//                            Color(0xFF1A1F24)
//                        )
//                    ),
//                    RoundedCornerShape(20.dp)
//                )
//                .padding(16.dp)
//        ) {
//
//            Row(verticalAlignment = Alignment.CenterVertically) {
//
//                Box(
//                    modifier = Modifier
//                        .size(50.dp)
//                        .background(Color(0xFF00E5A8), CircleShape),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text("⚡")
//                }
//
//                Spacer(Modifier.width(12.dp))
//
//                Column {
//                    Text("Fastest Server", color = Color.White)
//                    Text("Automatic Optimization", color = Color.Gray, fontSize = 12.sp)
//                }
//
//                Spacer(Modifier.weight(1f))
//
//                Column(horizontalAlignment = Alignment.End) {
//                    Text("Auto", color = Color(0xFF00E5A8))
//                    Text("▮▮▮", color = Color(0xFF00E5A8))
//                }
//            }
//        }
//
//        Spacer(Modifier.height(24.dp))
//
//        // 🌍 GLOBAL NETWORK HEADER
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Text("GLOBAL NETWORK", color = Color.Gray, fontSize = 12.sp)
//            Text("${servers.size} SERVERS ONLINE", color = Color.Gray, fontSize = 12.sp)
//        }
//
//        Spacer(Modifier.height(12.dp))
//
//        // 🔥 DYNAMIC SERVER LIST
//        servers.forEach { server ->
//
//            ServerItem(
//                flag = "🌍",
//                country = server.country,
//                city = "${server.city}",
//                ping = "${server.ping}ms",
//                isFavorite = server.ping < 50,
//                onClick = {
//                    SelectedServer.current = server
//                    println("SELECTED SERVER: $server")
//                }
//            )
//        }
//    }
//}


package com.example.vpn_ui.ui.screens

import androidx.compose.foundation.background
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
import com.example.vpn_ui.SelectedServer
import com.example.vpn_ui.ServerModel
import com.example.vpn_ui.ui.components.ServerItem

@Composable
fun ServerScreen() {
    val context = LocalContext.current
    val bg = Brush.verticalGradient(listOf(Color(0xFF020B0F), Color(0xFF041A1F)))
    var servers by remember { mutableStateOf<List<ServerModel>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try {
            val token = RetrofitClient.getAuthHeader(context)
            servers = RetrofitClient.api.getServers(token)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            isLoading = false
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().background(bg)
            .verticalScroll(rememberScrollState()).padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("SELECT SERVER", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("⚙️", color = Color.White)
        }

        Spacer(Modifier.height(20.dp))

        Box(modifier = Modifier.fillMaxWidth().background(Color(0xFF1A1F24), RoundedCornerShape(12.dp)).padding(14.dp)) {
            Text("Search for countries...", color = Color.Gray)
        }

        Spacer(Modifier.height(20.dp))

        // Fastest server card
        Box(
            modifier = Modifier.fillMaxWidth()
                .background(Brush.horizontalGradient(listOf(Color(0xFF00E5A8).copy(0.3f), Color(0xFF1A1F24))), RoundedCornerShape(20.dp))
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(50.dp).background(Color(0xFF00E5A8), CircleShape), contentAlignment = Alignment.Center) {
                    Text("⚡")
                }
                Spacer(Modifier.width(12.dp))
                Column {
                    Text("Fastest Server", color = Color.White)
                    Text("Automatic Optimization", color = Color.Gray, fontSize = 12.sp)
                }
                Spacer(Modifier.weight(1f))
                Column(horizontalAlignment = Alignment.End) {
                    Text("Auto", color = Color(0xFF00E5A8))
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("GLOBAL NETWORK", color = Color.Gray, fontSize = 12.sp)
            Text("${servers.size} SERVERS ONLINE", color = Color.Gray, fontSize = 12.sp)
        }

        Spacer(Modifier.height(12.dp))

        if (isLoading) {
            Box(modifier = Modifier.fillMaxWidth().height(100.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFF00E5A8))
            }
        } else {
            servers.forEach { server ->
                ServerItem(
                    flag = server.flag,
                    country = server.country,
                    city = server.city,
                    ping = "${server.ping}ms",
                    isFavorite = server.ping < 50,
                    onClick = {
                        SelectedServer.current = server
                        println("SELECTED SERVER: $server")
                    }
                )
            }
        }

        Spacer(Modifier.height(80.dp))
    }
}
