package com.example.vpn_ui

import android.content.Context
import android.util.Base64
import java.security.SecureRandom

object VpnKeyStorage {

    private const val PREF = "vpn_keys"
    private const val KEY_PRIVATE = "client_private_key"

    fun getOrCreatePrivateKey(context: Context): String {
        val prefs = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)

        var key = prefs.getString(KEY_PRIVATE, null)
        if (key == null) {
            key = generatePrivateKey()
            prefs.edit().putString(KEY_PRIVATE, key).apply()
        }
        return key
    }

    // ✅ WireGuard-compatible private key generator
    private fun generatePrivateKey(): String {
        val random = SecureRandom()
        val key = ByteArray(32)
        random.nextBytes(key)

        // Clamp for Curve25519 (MANDATORY)
        key[0] = (key[0].toInt() and 248).toByte()
        key[31] = (key[31].toInt() and 127).toByte()
        key[31] = (key[31].toInt() or 64).toByte()

        return Base64.encodeToString(key, Base64.NO_WRAP)
    }
}
