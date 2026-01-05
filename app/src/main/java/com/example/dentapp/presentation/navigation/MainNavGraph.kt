package com.example.dentapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.dentapp.presentation.PatientsScreen
import com.example.dentapp.presentation.PerioChartScreen

fun NavGraphBuilder.mainNavGraph(navHostController: NavHostController) {
    navigation(
        startDestination = Screen.Main.route,
        route = Screen.MainGraph.route
    ) {
        composable(Screen.Main.route) {
            MainScreen(navHostController)
        }

        composable(Screen.Patients.route) {
            PatientsScreen(navHostController)
        }

        composable(Screen.Home.route) {
            PatientsScreen(navHostController)
        }

        composable(Screen.PerioChart.route) {
            PerioChartScreen(navHostController)
        }
    }
}