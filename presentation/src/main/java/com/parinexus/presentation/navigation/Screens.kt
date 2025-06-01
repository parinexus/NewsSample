package com.parinexus.presentation.navigation


sealed class Screen(val route: String) {
    data object Home : Screen("Home")
    data object Search : Screen("Search")
    data object Favorites : Screen("Favorites")
}