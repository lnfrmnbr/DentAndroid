package com.example.dentapp.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.dentapp.presentation.patients.screen.PatientsScreen
import com.example.dentapp.presentation.appointment.screen.AppointmentScreen
import com.example.dentapp.presentation.calendar.screen.CalendarScreen
import com.example.dentapp.presentation.files.screen.FilesScreen
import com.example.dentapp.presentation.home.screen.HomeScreen
import com.example.dentapp.presentation.periochart.screen.PerioChartScreen
import com.example.dentapp.presentation.profile.screen.ProfileScreen

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

        composable(Screen.Appointment.route) {
            AppointmentScreen(navHostController)
        }

        composable(Screen.Files.route) {
            FilesScreen(navHostController)
        }

        composable(Screen.Profile.route) {
            ProfileScreen(navHostController)
        }
    }
}