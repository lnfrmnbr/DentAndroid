package com.example.dentapp.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.dentapp.ui.theme.LightCyan

@Composable
fun RootNavGraph(navHostController: NavHostController) {
    val mainViewModel: MainViewModel = hiltViewModel()
    val isLoggedIn by mainViewModel.isLoggedIn.collectAsState()
    val isLoading by mainViewModel.isLoading.collectAsState()

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn == true) {
            navHostController.navigate(Screen.MainGraph.route) {
                popUpTo(Screen.AuthGraph.route) { inclusive = true }
            }
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(LightCyan),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = LightCyan)
        }
        return
    }

    val startDestination = when {
        isLoggedIn == true -> Screen.MainGraph.route
        else -> Screen.AuthGraph.route
    }

    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {
        authNavGraph(
            navController = navHostController,
            onNavigateToMain = {
                mainViewModel.setLoggedIn(true)
                navHostController.navigate(Screen.MainGraph.route) {
                    popUpTo(Screen.AuthGraph.route) { inclusive = true }
                }
            }
        )
        mainNavGraph(navHostController)
    }
}