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
import com.example.vpn_ui.HistoryResponse
import com.example.vpn_ui.RetrofitClient
import com.example.vpn_ui.SessionItem

@Composable
fun ActivityScreen() {
    val context = LocalContext.current
    var historyData by remember { mutableStateOf<HistoryResponse?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var isConnected by remember { mutableStateOf(false) }
    var liveSession by remember { mutableStateOf<com.example.vpn_ui.SessionDetail?>(null) }

    LaunchedEffect(Unit) {
        try {
            val token = RetrofitClient.getAuthHeader(context)
            val current = RetrofitClient.api.getCurrentSession(token)
            if (current.active && current.session != null) {
                isConnected = true
                liveSession = current.session
            }
            historyData = RetrofitClient.api.getSessionHistory(token)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            isLoading = false
        }
    }

    val bg = Brush.verticalGradient(listOf(Color(0xFF020B0F), Color(0xFF041A1F)))

    Column(
        modifier = Modifier.fillMaxSize().background(bg)
            .verticalScroll(rememberScrollState()).padding(16.dp)
    ) {
        // Header
        Text("ACTIVITY", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(20.dp))

        if (isLoading) {
            Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFF00E5A8))
            }
        } else {
            // Live session card (if connected)
            if (isConnected && liveSession != null) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(listOf(Color(0xFF00E5A8).copy(0.2f), Color(0xFF1A1F24))),
                            RoundedCornerShape(20.dp)
                        ).padding(16.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(10.dp).background(Color(0xFF00E5A8), CircleShape))
                            Spacer(Modifier.width(8.dp))
                            Text("PROTECTED", color = Color(0xFF00E5A8), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(Modifier.height(8.dp))
                        Text("Connected", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        Text("${liveSession!!.server.country}, ${liveSession!!.server.city}", color = Color.Gray, fontSize = 13.sp)
                        Spacer(Modifier.height(16.dp))
                        Row {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("VIRTUAL IP", color = Color.Gray, fontSize = 11.sp)
                                Text(liveSession!!.virtualIp, color = Color(0xFF00E5A8), fontWeight = FontWeight.Bold)
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text("DURATION", color = Color.Gray, fontSize = 11.sp)
                                val h = liveSession!!.liveDuration / 3600
                                val m = (liveSession!!.liveDuration % 3600) / 60
                                val s = liveSession!!.liveDuration % 60
                                Text("%02d:%02d:%02d".format(h, m, s), color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
                Spacer(Modifier.height(20.dp))
            }

            // Stats row
            historyData?.stats?.let { stats ->
                Row {
                    StatCard(modifier = Modifier.weight(1f), label = "TOTAL SESSIONS", value = "${stats.totalSessions}")
                    Spacer(Modifier.width(10.dp))
                    StatCard(modifier = Modifier.weight(1f), label = "TOTAL TIME",
                        value = "%02dh %02dm".format(stats.totalDuration / 3600, (stats.totalDuration % 3600) / 60))
                }
                Spacer(Modifier.height(10.dp))
                Row {
                    StatCard(modifier = Modifier.weight(1f), label = "DOWNLOADED",
                        value = formatBytes(stats.totalDownloaded))
                    Spacer(Modifier.width(10.dp))
                    StatCard(modifier = Modifier.weight(1f), label = "UPLOADED",
                        value = formatBytes(stats.totalUploaded))
                }
                Spacer(Modifier.height(20.dp))
            }

            // Session history list
            Text("SESSION HISTORY", color = Color.Gray, fontSize = 12.sp)
            Spacer(Modifier.height(12.dp))

            val sessions = historyData?.sessions
            if (sessions.isNullOrEmpty()) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No session history yet", color = Color.Gray)
                }
            } else {
                sessions.forEach { session ->
                    SessionHistoryItem(session)
                    Spacer(Modifier.height(10.dp))
                }
            }
        }
        Spacer(Modifier.height(80.dp))
    }
}

@Composable
fun StatCard(modifier: Modifier, label: String, value: String) {
    Box(modifier = modifier.background(Color(0xFF1A1F24).copy(0.6f), RoundedCornerShape(16.dp)).padding(16.dp)) {
        Column {
            Text(label, color = Color.Gray, fontSize = 11.sp)
            Spacer(Modifier.height(4.dp))
            Text(value, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}

@Composable
fun SessionHistoryItem(session: SessionItem) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .background(Color(0xFF1A1F24).copy(0.6f), RoundedCornerShape(16.dp)).padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(42.dp).background(Color(0xFF00E5A8).copy(0.1f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(session.server.flag, fontSize = 20.sp)
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("${session.server.country}, ${session.server.city}", color = Color.White, fontWeight = FontWeight.Bold)
                Text(formatDate(session.startTime), color = Color.Gray, fontSize = 12.sp)
            }
            Column(horizontalAlignment = Alignment.End) {
                val h = session.duration / 3600
                val m = (session.duration % 3600) / 60
                val s = session.duration % 60
                Text("%02d:%02d:%02d".format(h, m, s), color = Color(0xFF00E5A8), fontSize = 13.sp)
                Text(formatBytes(session.bytesDownloaded + session.bytesUploaded), color = Color.Gray, fontSize = 11.sp)
            }
        }
    }
}

fun formatBytes(bytes: Long): String {
    return when {
        bytes >= 1_073_741_824 -> "%.1f GB".format(bytes / 1_073_741_824.0)
        bytes >= 1_048_576 -> "%.1f MB".format(bytes / 1_048_576.0)
        bytes >= 1024 -> "%.1f KB".format(bytes / 1024.0)
        else -> "$bytes B"
    }
}

fun formatDate(dateStr: String): String {
    return try {
        dateStr.take(10) // "2026-04-05"
    } catch (e: Exception) { dateStr }
}
