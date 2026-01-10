package com.example.dentapp.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dentapp.presentation.home.screen.HomeScreen
import com.example.dentapp.presentation.PatientsScreen
import com.example.dentapp.presentation.periochart.screen.PerioChartScreen
import com.example.dentapp.ui.theme.CadetBlue

@Composable
fun MainScreen(navController: NavHostController) {
    val mainNavController = rememberNavController()

    Scaffold(
        topBar = { NavBar(mainNavController) }
    ) { innerPadding ->
        NavHost(
            navController = mainNavController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
                .background(CadetBlue)
        ) {
            composable(Screen.Home.route) { HomeScreen(navController = navController) }
            composable(Screen.Patients.route) { PatientsScreen(navController = navController) }
            composable(Screen.PerioChart.route) { PerioChartScreen(navController = navController) }
        }
    }
}