package com.example.dentapp.presentation.navigation

sealed class Screen(val route: String) {
    data object AuthGraph : Screen("auth_graph")
    data object MainGraph : Screen("main_graph")
    data object PerioChartGraph : Screen("perio_chart_graph")

    data object SignIn : Screen("sign_in")
    data object SignUp : Screen("sign_up")

    data object Main : Screen("main")
    data object Home : Screen("home")
    data object Calendar : Screen("calendar")
    data object Appointment : Screen("appointment")
    data object Files : Screen("files")
    data object Profile : Screen("profile")

    data object PerioChart : Screen("perio_chart")

    data object Patients : Screen("patients")
}