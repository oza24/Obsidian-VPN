package com.example.vpn_ui.ui

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vpn_ui.ServerModel

@Composable
fun ServerCard(server: ServerModel, isConnected: Boolean) {

    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E2A33)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text("Browsing safely from", color = Color.Gray, fontSize = 12.sp)

            Spacer(Modifier.height(6.dp))

            Text(
                "${server.country} • ${server.city}",
                color = Color.White,
                fontSize = 18.sp
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = if (isConnected) "Connected" else "Disconnected",
                color = if (isConnected) Color.Green else Color.Red
            )
        }
    }
}