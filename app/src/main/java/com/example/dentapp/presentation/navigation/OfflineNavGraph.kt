package com.example.dentapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.offlineNavGraph(navHostController: NavHostController) {
    navigation(
        startDestination = Screen.Main.route,
        route = Screen.OfflineGraph.route
    ) {
        composable(Screen.Main.route) {
            MainScreen(navHostController, true)
        }
    }
}