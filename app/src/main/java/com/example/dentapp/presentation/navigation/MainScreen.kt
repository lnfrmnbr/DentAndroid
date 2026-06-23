package com.example.dentapp.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.ControlPoint
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dentapp.R
import com.example.dentapp.presentation.calendar.screen.CalendarScreen
import com.example.dentapp.presentation.files.screen.FilesScreen
import com.example.dentapp.presentation.home.screen.HomeScreen
import com.example.dentapp.presentation.home.viewmodel.HomeViewModel.HomeTabs
import com.example.dentapp.presentation.patients.screen.PatientsScreen
import com.example.dentapp.presentation.profile.screen.ProfileScreen
import com.example.dentapp.ui.theme.Blue
import com.example.dentapp.ui.theme.LightBlue
import com.example.dentapp.ui.theme.Montserrat
import com.example.dentapp.ui.theme.SkyBlue
import com.example.dentapp.ui.theme.VeryDarkBlue
import com.example.dentapp.ui.theme.VeryLightBlue
import com.example.dentapp.ui.theme.White
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MainScreen(rootNavController: NavHostController, isOfflineMode: Boolean = false) {
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
        if (!isOfflineMode) {
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
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(LightBlue)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TabCard(
                            "Начать приём",
                            stringResource(R.string.periodontal_chart),
                            Icons.Default.AddCircle
                        ) {  }

                        TabCard(
                            "Настройки",
                            stringResource(R.string.your_data),
                            Icons.Default.Settings
                        ) {  }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                            .background(Color.White)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TabCard(
    title: String,
    subTitle: String,
    icon: ImageVector,
    onClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(VeryLightBlue)
            .clickable { onClick(title) },
        contentAlignment = Alignment.BottomStart
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Blue,
                modifier = Modifier.size(30.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = title,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Blue,
                lineHeight = 15.sp
            )

            Text(
                text = subTitle,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = SkyBlue,
                lineHeight = 12.sp
            )
        }
    }
}