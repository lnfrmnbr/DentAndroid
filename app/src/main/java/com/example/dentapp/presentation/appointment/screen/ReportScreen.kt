package com.example.dentapp.presentation.appointment.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.dentapp.presentation.appointment.viewmodel.AppointmentViewModel


@Composable
fun ReportScreen(
    navController: NavHostController,
    viewModel: AppointmentViewModel = hiltViewModel()
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
}