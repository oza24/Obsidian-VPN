package com.example.vpn_ui.ui.components

import android.R.attr.shape
import android.R.attr.width
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BottomNav(
    current: String,
    onChange: (String) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFF0E1A1F).copy(alpha = 0.9f), RoundedCornerShape(30.dp))
            .padding(vertical = 10.dp)
            .border(
                width = 1.dp,
                color = Color(0xFF00E5A8).copy(alpha = 0.3f),
                shape = RoundedCornerShape(30.dp)
            ),
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        NavItem("home", current, onChange)
        NavItem("servers", current, onChange)
        NavItem("activity", current, onChange)
        NavItem("profile", current, onChange)
    }
}

@Composable
fun NavItem(
    name: String,
    current: String,
    onClick: (String) -> Unit
) {

    val isSelected = name == current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // 🔥 USING BUTTON INSTEAD OF CLICKABLE (NO CRASH)
        TextButton(
            onClick = { onClick(name) }
        ) {

            Text(
                text = name.uppercase(),
                color = if (isSelected) Color(0xFF00E5A8) else Color.Gray
            )
        }

        if (isSelected) {
            Box(
                modifier = Modifier
                    .height(3.dp)
                    .width(20.dp)
                    .background(Color(0xFF00E5A8))
            )
        }
    }
}