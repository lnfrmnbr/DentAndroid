package com.example.dentapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.dentapp.presentation.periochart.screen.DetailToothScreen
import com.example.dentapp.presentation.periochart.screen.PerioChartScreen

fun NavGraphBuilder.perioChartNavGraph(navHostController: NavHostController) {
    navigation(
        startDestination = Screen.PerioChart.route,
        route = Screen.PerioChartGraph.route
    ) {
        composable(Screen.PerioChart.route) {
            PerioChartScreen(navHostController)
        }

        composable(Screen.PerioChart.route) {
            PerioChartScreen(navHostController)
        }

        composable(Screen.DetailToothScreen.route) {
            DetailToothScreen(navHostController)
       }
    }
}