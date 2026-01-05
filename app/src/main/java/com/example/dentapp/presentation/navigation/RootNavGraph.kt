package com.example.dentapp.presentation.navigation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.dentapp.BuildConfig
import com.example.dentapp.data.oauth.GoogleAuthUiClient
import com.example.dentapp.presentation.auth.viewmodel.SignInViewModel
import com.example.dentapp.ui.theme.LightCyan
import kotlinx.coroutines.launch

@Composable
fun RootNavGraph(navHostController: NavHostController) {
    val mainViewModel: MainViewModel = hiltViewModel()
    val signInViewModel: SignInViewModel = hiltViewModel()
    val isLoggedIn by mainViewModel.isLoggedIn.collectAsState()
    val isLoading by mainViewModel.isLoading.collectAsState()

    val context = LocalContext.current
    val googleAuthUiClient = remember {
        GoogleAuthUiClient(context, BuildConfig.GOOGLE_CLIENT_ID)
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn == true) {
            navHostController.navigate(Screen.MainGraph.route) {
                popUpTo(Screen.AuthGraph.route) { inclusive = true }
            }
        }
    }

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            scope.launch {
                try {
                    val token = googleAuthUiClient.getTokenFromIntent(result.data)
                    if (token != null) {
                        signInViewModel.signInWithGoogle(token) {
                            mainViewModel.setLoggedIn(true)
                            navHostController.navigate(Screen.MainGraph.route) {
                                popUpTo(Screen.AuthGraph.route) { inclusive = true }
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    )

    val onGoogleSignIn = {
        try {
            val intent = googleAuthUiClient.getSignInIntent()
            googleSignInLauncher.launch(intent)
        } catch (e: Exception) {
            e.printStackTrace()
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
            onGoogleSignIn = onGoogleSignIn,
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