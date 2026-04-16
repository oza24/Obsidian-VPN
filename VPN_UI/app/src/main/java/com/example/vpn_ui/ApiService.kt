//package com.example.vpn_ui
//
//import retrofit2.http.*
//
//// ── Request bodies ──────────────────────────────
//data class RegisterRequest(val name: String, val email: String, val password: String)
//data class LoginRequest(val email: String, val password: String)
//data class StartSessionRequest(val serverId: String, val virtualIp: String = "10.0.1.3")
//data class EndSessionRequest(val bytesDownloaded: Long = 0, val bytesUploaded: Long = 0)
//
//// ── Response bodies ─────────────────────────────
//data class AuthResponse(val token: String, val user: UserModel)
//data class UserModel(
//    val id: String,
//    val name: String,
//    val email: String,
//    val subscription: SubscriptionModel,
//    val devices: Int,
//    val maxDevices: Int
//)
//data class SubscriptionModel(val plan: String, val activeSince: String)
//data class SessionResponse(val _id: String, val virtualIp: String, val startTime: String, val isActive: Boolean)
//data class CurrentSessionResponse(val active: Boolean, val session: SessionDetail?)
//data class SessionDetail(
//    val _id: String,
//    val virtualIp: String,
//    val startTime: String,
//    val liveDuration: Int,
//    val server: ServerModel
//)
//data class HistoryResponse(
//    val sessions: List<SessionItem>,
//    val stats: SessionStats
//)
//data class SessionItem(
//    val _id: String,
//    val startTime: String,
//    val endTime: String?,
//    val duration: Int,
//    val bytesDownloaded: Long,
//    val bytesUploaded: Long,
//    val server: ServerModel
//)
//data class SessionStats(
//    val totalDownloaded: Long,
//    val totalUploaded: Long,
//    val totalDuration: Int,
//    val totalSessions: Int
//)
//
//interface ApiService {
//
//    // ── Auth ──────────────────────────────────────
//    @POST("api/auth/register")
//    suspend fun register(@Body request: RegisterRequest): AuthResponse
//
//    @POST("api/auth/login")
//    suspend fun login(@Body request: LoginRequest): AuthResponse
//
//    @GET("api/auth/me")
//    suspend fun getMe(@Header("Authorization") token: String): UserModel
//
//    // ── Servers ───────────────────────────────────
//    @GET("api/vpn/servers")
//    suspend fun getServers(@Header("Authorization") token: String): List<ServerModel>
//
//    // ── Sessions ──────────────────────────────────
//    @POST("api/sessions/start")
//    suspend fun startSession(
//        @Header("Authorization") token: String,
//        @Body request: StartSessionRequest
//    ): SessionResponse
//
//    @POST("api/sessions/end")
//    suspend fun endSession(
//        @Header("Authorization") token: String,
//        @Body request: EndSessionRequest
//    ): SessionResponse
//
//    @GET("api/sessions/current")
//    suspend fun getCurrentSession(@Header("Authorization") token: String): CurrentSessionResponse
//
//    @GET("api/sessions/history")
//    suspend fun getSessionHistory(@Header("Authorization") token: String): HistoryResponse
//
//    // ── User ──────────────────────────────────────
//    @GET("api/user/profile")
//    suspend fun getProfile(@Header("Authorization") token: String): UserModel
//}


package com.example.vpn_ui

import retrofit2.http.*

// ── Request bodies ──────────────────────────────────────
data class RegisterRequest(val name: String, val email: String, val password: String)
data class LoginRequest(val email: String, val password: String)
data class StartSessionRequest(val serverId: String, val virtualIp: String = "10.0.1.3")
data class EndSessionRequest(val bytesDownloaded: Long = 0, val bytesUploaded: Long = 0)

// ── Response models ─────────────────────────────────────
data class AuthResponse(val token: String, val user: UserModel)
data class UserModel(
    val id: String,
    val name: String,
    val email: String,
    val subscription: SubscriptionModel,
    val devices: Int,
    val maxDevices: Int
)
data class SubscriptionModel(val plan: String, val activeSince: String)

data class ServerModel(
    val _id: String? = null,
    val country: String,
    val city: String,
    val ping: Int,
    val ip: String,
    val publicKey: String,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val flag: String = "🌍",
    val load: Int = 0
)

data class SessionResponse(val _id: String, val virtualIp: String, val startTime: String, val isActive: Boolean)
data class CurrentSessionResponse(val active: Boolean, val session: SessionDetail?)
data class SessionDetail(
    val _id: String,
    val virtualIp: String,
    val startTime: String,
    val liveDuration: Int,
    val server: ServerModel
)
data class HistoryResponse(val sessions: List<SessionItem>, val stats: SessionStats)
data class SessionItem(
    val _id: String,
    val startTime: String,
    val endTime: String?,
    val duration: Int,
    val bytesDownloaded: Long,
    val bytesUploaded: Long,
    val server: ServerModel
)
data class SessionStats(
    val totalDownloaded: Long,
    val totalUploaded: Long,
    val totalDuration: Int,
    val totalSessions: Int
)

// ── API Interface ───────────────────────────────────────
interface ApiService {

    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): AuthResponse

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @GET("api/user/profile")
    suspend fun getProfile(@Header("Authorization") token: String): UserModel

    @GET("api/vpn/servers")
    suspend fun getServers(@Header("Authorization") token: String): List<ServerModel>

    @POST("api/sessions/start")
    suspend fun startSession(@Header("Authorization") token: String, @Body request: StartSessionRequest): SessionResponse

    @POST("api/sessions/end")
    suspend fun endSession(@Header("Authorization") token: String, @Body request: EndSessionRequest): SessionResponse

    @GET("api/sessions/current")
    suspend fun getCurrentSession(@Header("Authorization") token: String): CurrentSessionResponse

    @GET("api/sessions/history")
    suspend fun getSessionHistory(@Header("Authorization") token: String): HistoryResponse
}
