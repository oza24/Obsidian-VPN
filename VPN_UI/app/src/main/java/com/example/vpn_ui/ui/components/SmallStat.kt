package com.example.vpn_ui.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SmallStat(title: String, value: String, subtitle: String, modifier: Modifier) {

    Box(
        modifier = modifier
            .background(Color(0xFF1A1F24), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(title, color = Color.Gray, fontSize = 12.sp)
            Spacer(Modifier.height(6.dp))
            Text(value, color = Color.White, fontWeight = FontWeight.Bold)
            Text(subtitle, color = Color.Gray, fontSize = 10.sp)
        }
    }
}