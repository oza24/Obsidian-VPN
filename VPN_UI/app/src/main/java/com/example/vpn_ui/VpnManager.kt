package com.example.vpn_ui

import android.content.Context
import com.wireguard.android.backend.GoBackend
import com.wireguard.android.backend.Tunnel
import com.wireguard.config.Config
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VpnManager(private val context: Context) {

    private val backend = GoBackend(context)

    private val tunnel = object : Tunnel {
        override fun getName() = "MyVPN"
        override fun onStateChange(state: Tunnel.State) {}
    }

    suspend fun connect(server: ServerModel) {
        withContext(Dispatchers.IO) {          // ← ADD THIS
            val privateKey = "6GNcN4Sp4xIci+WrDaq6lflSh5knk/w65wPHSI70i1I="

            val config = Config.parse(
                """
                [Interface]
                PrivateKey = $privateKey
                Address = 10.0.1.3/32
                DNS = 1.1.1.1

                [Peer]
                PublicKey = ${server.publicKey}
                Endpoint = ${server.ip}:51820
                AllowedIPs = 0.0.0.0/0
                PersistentKeepalive = 25
                """.trimIndent().byteInputStream()
            )

            backend.setState(tunnel, Tunnel.State.UP, config)
        }
    }

    suspend fun disconnect() {
        withContext(Dispatchers.IO) {          // ← ADD THIS
            backend.setState(tunnel, Tunnel.State.DOWN, null)
        }
    }
}