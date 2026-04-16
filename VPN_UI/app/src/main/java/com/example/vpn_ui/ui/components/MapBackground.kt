package com.example.vpn_ui.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import com.example.vpn_ui.R

@Composable
fun MapBackground() {

    Image(
        painter = painterResource(id = R.drawable.world_map), // add image
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        alpha = 0.15f,
        colorFilter = ColorFilter.tint(androidx.compose.ui.graphics.Color(0xFF00E5A8))
    )
}