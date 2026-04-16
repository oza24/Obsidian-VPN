package com.example.vpn_ui.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GlowConnectButton(isConnected: Boolean, onClick: () -> Unit) {

    val infiniteTransition = rememberInfiniteTransition()

    val glow by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(contentAlignment = Alignment.Center) {

        // glow
        Box(
            modifier = Modifier
                .size(260.dp)
                .blur(80.dp)
                .background(
                    Color(0xFF00E5A8).copy(alpha = glow),
                    CircleShape
                )
        )

        // button
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(Color(0xFF0B1F1B), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                if (isConnected) "DISCONNECT" else "CONNECT",
                color = Color(0xFF00E5A8),
                fontSize = 18.sp
            )
        }
    }
}