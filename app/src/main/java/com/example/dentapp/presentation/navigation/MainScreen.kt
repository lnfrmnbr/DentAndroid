package com.example.dentapp.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dentapp.presentation.PatientsScreen
import com.example.dentapp.presentation.home.screen.HomeScreen
import com.example.dentapp.presentation.periochart.screen.PerioChartScreen
import com.example.dentapp.ui.theme.CadetBlue

import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MainScreen(navController: NavHostController) {
    val mainNavController = rememberNavController()
    val backgroundColor = CadetBlue

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = backgroundColor,
            darkIcons = backgroundColor.luminance() > 0.5f // Автоопределение цвета иконок
        )

        systemUiController.setNavigationBarColor(
            color = backgroundColor, // Цвет системной навигационной панели
            darkIcons = backgroundColor.luminance() > 0.5f,
            navigationBarContrastEnforced = false // Отключаем контрастность
        )
    }

    val systemBars = WindowInsets.systemBars
    val navigationBars = WindowInsets.navigationBars

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        NavHost(
            navController = mainNavController,
            startDestination = Screen.Home.route,
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(systemBars)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navController = navController)
            }
            composable(Screen.Patients.route) {
                PatientsScreen(navController = navController)
            }
            composable(Screen.PerioChart.route) {
                PerioChartScreen(navController = navController)
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .windowInsetsPadding(navigationBars)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                color = backgroundColor
            ) {
                NavBar(mainNavController)
            }
        }
    }
}