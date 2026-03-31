package com.example.dentapp.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dentapp.presentation.calendar.screen.CalendarScreen
import com.example.dentapp.presentation.files.screen.FilesScreen
import com.example.dentapp.presentation.home.screen.HomeScreen
import com.example.dentapp.presentation.patients.screen.PatientsScreen
import com.example.dentapp.presentation.profile.screen.ProfileScreen
import com.example.dentapp.ui.theme.LightBlue
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MainScreen(rootNavController: NavHostController) {
    val mainNavController = rememberNavController()
    val backgroundColor = Color.White

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = LightBlue,
            darkIcons = backgroundColor.luminance() > 0.5f
        )

        systemUiController.setNavigationBarColor(
            color = Color.White,
            darkIcons = backgroundColor.luminance() > 0.5f,
            navigationBarContrastEnforced = false
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
                HomeScreen(navController = mainNavController)
            }
            composable(Screen.Calendar.route) {
                CalendarScreen(navController = mainNavController)
            }
            composable(Screen.Files.route) {
                FilesScreen(navController = mainNavController)
            }
            composable(Screen.Patients.route) {
                PatientsScreen(navController = mainNavController)
            }
            composable(Screen.Profile.route) {
                ProfileScreen(navController = mainNavController)
            }
        }

        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .windowInsetsPadding(navigationBars),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 16.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            NavBar(mainNavController, rootNavController)
        }
    }
}