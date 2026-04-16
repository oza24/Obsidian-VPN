package com.example.vpn_ui.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun StatCard(title: String, value: String, modifier: Modifier) {

    Box(
        modifier = modifier
            .background(Color(0xFF1A1F24), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(title, color = Color.Gray)
            Spacer(Modifier.height(6.dp))
            Text(value, color = Color.White)
        }
    }
}