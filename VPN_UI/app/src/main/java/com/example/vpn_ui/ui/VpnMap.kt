package com.example.vpn_ui.ui

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vpn_ui.ServerModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*

@Composable
fun VpnMap(server: ServerModel) {

    val cameraState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(server.latitude, server.longitude),
            4f
        )
    }

    LaunchedEffect(server) {
        cameraState.animate(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(server.latitude, server.longitude),
                5f
            ),
            1500
        )
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        cameraPositionState = cameraState
    ) {
        Marker(
            state = MarkerState(
                position = LatLng(server.latitude, server.longitude)
            ),
            title = server.city
        )
    }
}