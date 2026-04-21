package com.example.dentapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.dentapp.domain.model.appointment.Appointment

@Composable
fun RootPerioChartNavGraph(navHostController: NavHostController, appointment: Appointment?) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.PerioChartGraph.route
    ) {
        perioChartNavGraph(navHostController, appointment)
    }
}