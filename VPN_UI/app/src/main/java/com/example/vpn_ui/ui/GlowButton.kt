package com.example.vpn_ui.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GlowButton(isConnected: Boolean, onClick: () -> Unit) {

    val infiniteTransition = rememberInfiniteTransition()

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(contentAlignment = Alignment.Center) {

        // Glow effect
        Box(
            modifier = Modifier
                .size((150 * scale).dp)
                .background(
                    if (isConnected) Color(0x3300E676) else Color(0x332196F3),
                    CircleShape
                )
        )

        // Main button
        Button(
            onClick = onClick,
            shape = CircleShape,
            modifier = Modifier.size(120.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isConnected) Color(0xFF00E676) else Color(0xFF2196F3)
            )
        ) {
            Text(if (isConnected) "Disconnect" else "Connect")
        }
    }
}