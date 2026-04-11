package com.example.dentapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun RootPerioChartNavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.PerioChartGraph.route
    ) {
        perioChartNavGraph(navHostController)
    }
}