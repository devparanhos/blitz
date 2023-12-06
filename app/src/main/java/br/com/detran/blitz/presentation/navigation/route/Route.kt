package br.com.detran.blitz.presentation.navigation.route

sealed class Route(val name: String) {
    object Home : Route(name = "home")
}
