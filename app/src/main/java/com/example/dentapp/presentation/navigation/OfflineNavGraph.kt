package com.example.dentapp.presentation.navigation

import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.dentapp.presentation.appointment.screen.FinishAppointmentScreen
import com.example.dentapp.presentation.appointment.screen.ReportScreen
import com.example.dentapp.presentation.appointment.screen.StartAppointmentScreen
import com.example.dentapp.presentation.appointment.viewmodel.AppointmentViewModel

fun NavGraphBuilder.offlineNavGraph(navHostController: NavHostController) {
    navigation(
        startDestination = Screen.Main.route,
        route = Screen.OfflineGraph.route
    ) {
        composable(Screen.Main.route) {
            MainScreen(navHostController, true)
        }

        composable(Screen.StartAppointment.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navHostController.getBackStackEntry(Screen.OfflineGraph.route)
            }
            val viewModel: AppointmentViewModel = hiltViewModel(parentEntry)

            StartAppointmentScreen(navHostController, viewModel)
        }

        composable(Screen.FinishAppointment.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navHostController.getBackStackEntry(Screen.OfflineGraph.route)
            }
            val viewModel: AppointmentViewModel = hiltViewModel(parentEntry)

            FinishAppointmentScreen(navHostController, viewModel)
        }

        composable(Screen.Report.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navHostController.getBackStackEntry(Screen.OfflineGraph.route)
            }
            val viewModel: AppointmentViewModel = hiltViewModel(parentEntry)

            ReportScreen(navHostController, viewModel)
        }
    }
}