package com.example.dentapp.presentation.navigation

import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.dentapp.presentation.appointment.screen.FinishAppointmentScreen
import com.example.dentapp.presentation.appointment.screen.StartAppointmentScreen
import com.example.dentapp.presentation.appointment.viewmodel.AppointmentViewModel
import com.example.dentapp.presentation.calendar.screen.CalendarScreen
import com.example.dentapp.presentation.files.screen.FilesScreen
import com.example.dentapp.presentation.home.screen.HomeScreen
import com.example.dentapp.presentation.patients.screen.PatientsScreen
import com.example.dentapp.presentation.profile.screen.ProfileScreen
import com.example.dentapp.presentation.appointment.screen.ReportScreen

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
            HomeScreen(navHostController)
        }

        composable(Screen.Calendar.route) {
            CalendarScreen(navHostController)
        }

        composable(Screen.StartAppointment.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navHostController.getBackStackEntry(Screen.MainGraph.route)
            }
            val viewModel: AppointmentViewModel = hiltViewModel(parentEntry)

            StartAppointmentScreen(navHostController, viewModel)
        }

        composable(Screen.FinishAppointment.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navHostController.getBackStackEntry(Screen.MainGraph.route)
            }
            val viewModel: AppointmentViewModel = hiltViewModel(parentEntry)

            FinishAppointmentScreen(navHostController, viewModel)
        }

        composable(Screen.Report.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navHostController.getBackStackEntry(Screen.MainGraph.route)
            }
            val viewModel: AppointmentViewModel = hiltViewModel(parentEntry)

            ReportScreen(navHostController, viewModel)
        }

        composable(Screen.Files.route) {
            FilesScreen(navHostController)
        }

        composable(Screen.Profile.route) {
            ProfileScreen(navHostController)
        }
    }
}