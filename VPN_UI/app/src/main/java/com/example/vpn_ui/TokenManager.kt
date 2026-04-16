//package com.example.vpn_ui
//
//import android.content.Context
//import androidx.datastore.preferences.core.edit
//import androidx.datastore.preferences.core.stringPreferencesKey
//import androidx.datastore.preferences.preferencesDataStore
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.flow.map
//
//val Context.dataStore by preferencesDataStore(name = "vpn_prefs")
//
//object TokenManager {
//
//    private val TOKEN_KEY = stringPreferencesKey("jwt_token")
//    private val USER_NAME_KEY = stringPreferencesKey("user_name")
//    private val USER_EMAIL_KEY = stringPreferencesKey("user_email")
//
//    suspend fun saveToken(context: Context, token: String) {
//        context.dataStore.edit { it[TOKEN_KEY] = token }
//    }
//
//    suspend fun getToken(context: Context): String? {
//        return context.dataStore.data.map { it[TOKEN_KEY] }.first()
//    }
//
//    suspend fun saveUser(context: Context, name: String, email: String) {
//        context.dataStore.edit {
//            it[USER_NAME_KEY] = name
//            it[USER_EMAIL_KEY] = email
//        }
//    }
//
//    suspend fun getUserName(context: Context): String? {
//        return context.dataStore.data.map { it[USER_NAME_KEY] }.first()
//    }
//
//    suspend fun getUserEmail(context: Context): String? {
//        return context.dataStore.data.map { it[USER_EMAIL_KEY] }.first()
//    }
//
//    suspend fun clearAll(context: Context) {
//        context.dataStore.edit { it.clear() }
//    }
//
//    suspend fun isLoggedIn(context: Context): Boolean {
//        return getToken(context) != null
//    }
//}




package com.example.vpn_ui

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "vpn_prefs")

object TokenManager {
    private val TOKEN_KEY = stringPreferencesKey("jwt_token")
    private val USER_NAME_KEY = stringPreferencesKey("user_name")
    private val USER_EMAIL_KEY = stringPreferencesKey("user_email")

    suspend fun saveToken(context: Context, token: String) {
        context.dataStore.edit { it[TOKEN_KEY] = token }
    }

    suspend fun getToken(context: Context): String? {
        return context.dataStore.data.map { it[TOKEN_KEY] }.first()
    }

    suspend fun saveUser(context: Context, name: String, email: String) {
        context.dataStore.edit { it[USER_NAME_KEY] = name; it[USER_EMAIL_KEY] = email }
    }

    suspend fun getUserName(context: Context): String? =
        context.dataStore.data.map { it[USER_NAME_KEY] }.first()

    suspend fun getUserEmail(context: Context): String? =
        context.dataStore.data.map { it[USER_EMAIL_KEY] }.first()

    suspend fun clearAll(context: Context) {
        context.dataStore.edit { it.clear() }
    }

    suspend fun isLoggedIn(context: Context): Boolean = getToken(context) != null
}
