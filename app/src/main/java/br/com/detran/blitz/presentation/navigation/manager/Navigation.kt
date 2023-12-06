package br.com.detran.blitz.presentation.navigation.manager

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.detran.blitz.presentation.navigation.route.Route
import br.com.detran.blitz.presentation.feature.home.view.HomeScreen


@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.Home.name) {
        composable(route = Route.Home.name) {
            HomeScreen()
        }
    }
}