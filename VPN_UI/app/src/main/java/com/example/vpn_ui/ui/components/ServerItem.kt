package com.example.vpn_ui.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ServerItem(
    flag: String,
    country: String,
    city: String,
    ping: String,
    isFavorite: Boolean,
    onClick: (() -> Unit)? = null

) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .background(Color(0xFF121A20), RoundedCornerShape(12.dp))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onClick?.invoke()
            }
            .padding(14.dp)
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {

            Text(flag, fontSize = 22.sp)

            Spacer(Modifier.width(12.dp))

            Column {
                Text(country, color = Color.White)
                Text(city, color = Color.Gray, fontSize = 12.sp)
            }

            Spacer(Modifier.weight(1f))

            Column(horizontalAlignment = Alignment.End) {
                Text(ping, color = Color.White)
                Text(
                    if (isFavorite) "★" else "☆",
                    color = if (isFavorite) Color(0xFF00E5A8) else Color.Gray
                )
            }
        }
    }
}