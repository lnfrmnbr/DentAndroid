package com.example.dentapp.presentation.navigation

import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.dentapp.presentation.periochart.screen.DetailToothScreen
import com.example.dentapp.presentation.periochart.screen.PerioChartScreen
import com.example.dentapp.presentation.periochart.viewmodel.PerioChartViewModel

fun NavGraphBuilder.perioChartNavGraph(navHostController: NavHostController) {
    navigation(
        startDestination = Screen.PerioChart.route,
        route = Screen.PerioChartGraph.route
    ) {
        composable(Screen.PerioChart.route) { backStackEntry ->

            val parentEntry = remember(backStackEntry) {
                navHostController.getBackStackEntry(Screen.PerioChartGraph.route)
            }

            val viewModel: PerioChartViewModel = hiltViewModel(parentEntry)

            PerioChartScreen(
                navController = navHostController,
                viewModel = viewModel
            )
        }

        composable(
            route = Screen.DetailToothScreen.route,
            arguments = listOf(
                navArgument("teethIds") {
                    type = NavType.StringType
                    defaultValue = "18"
                }
            )
        ) { backStackEntry ->

            val parentEntry = remember(backStackEntry) {
                navHostController.getBackStackEntry(Screen.PerioChartGraph.route)
            }

            val viewModel: PerioChartViewModel = hiltViewModel(parentEntry)

            val teethIdsArg = backStackEntry.arguments?.getString("teethIds") ?: ""

            val teethIds = remember(teethIdsArg) {
                if (teethIdsArg.isEmpty()) emptyList()
                else teethIdsArg.split(",").mapNotNull { it.toIntOrNull() }
            }

            DetailToothScreen(
                navController = navHostController,
                viewModel = viewModel,
                teethIds = teethIds
            )
        }
    }
}