package com.example.dentapp.presentation.navigation

sealed class Screen(val route: String) {
    data object AuthGraph : Screen("auth_graph")
    data object MainGraph : Screen("main_graph")

    data object SignIn : Screen("sign_in")
    data object SignUp : Screen("sign_up")

    data object Main : Screen("main")
}