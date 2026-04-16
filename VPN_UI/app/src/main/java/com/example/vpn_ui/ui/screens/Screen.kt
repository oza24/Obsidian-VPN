package com.example.vpn_ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Servers : Screen("servers")
    object Activity : Screen("activity")
    object Profile : Screen("profile")
}