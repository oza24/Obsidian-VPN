



package com.example.vpn_ui

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // ← Keep your existing IP here
    private const val BASE_URL = "http://192.168.237.115:5000/" // keep your existing IP 

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    // Call this everywhere instead of writing "Bearer $token" manually
    suspend fun getAuthHeader(context: Context): String {
        val token = TokenManager.getToken(context) ?: ""
        return "Bearer $token"
    }
}
