package com.example.dentapp.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.dentapp.R
import com.example.dentapp.ui.theme.LightBlue
import com.example.dentapp.ui.theme.Montserrat
import com.example.dentapp.ui.theme.NewBlue
import com.example.dentapp.ui.theme.VeryDarkBlue

@Composable
fun NavBar(navController: NavController, rootNavController: NavController) {
    val items =
        listOf(
            BottomNavItem.Home,
            BottomNavItem.Calendar,
            BottomNavItem.Appointment,
            BottomNavItem.Files,
            BottomNavItem.Profile
        )

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
            .background(Color.White),
        containerColor = Color.Transparent,
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        items.forEach { navItem ->
            NavigationBarItem(
                selected = currentRoute == navItem.route,
                onClick = {
                    if (navItem is BottomNavItem.Appointment) {
                        rootNavController.navigate(Screen.Appointment.route)
                    } else {
                        navController.navigate(navItem.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Image(
                        painter = painterResource(id = navItem.iconRes),
                        contentDescription = stringResource(navItem.titleRes),
                        modifier = Modifier.size(20.dp),
                        colorFilter = ColorFilter.tint(NewBlue)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NewBlue,
                    selectedTextColor = NewBlue,
                    indicatorColor = LightBlue
                ),
                label = {
                    Text(
                        stringResource(navItem.titleRes),
                        color = if (currentRoute == navItem.route) {
                            VeryDarkBlue
                        } else {
                            NewBlue
                        },
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Normal,
                        fontSize = 10.5.sp,
                        maxLines = 1
                    )
                }
            )
        }
    }
}

sealed class BottomNavItem(
    @DrawableRes val iconRes: Int,
    @StringRes val titleRes: Int,
    val route: String
) {
    data object Home : BottomNavItem(
        R.drawable.home_icon,
        R.string.home,
        Screen.Home.route
    )

    data object Calendar : BottomNavItem(
        R.drawable.calendar_icon,
        R.string.calendar,
        Screen.Calendar.route
    )

    data object Appointment : BottomNavItem(
        R.drawable.plus_icon,
        R.string.start,
        Screen.Appointment.route
    )

    data object Files : BottomNavItem(
        R.drawable.files_icon,
        R.string.files,
        Screen.Files.route
    )

    data object Profile : BottomNavItem(
        R.drawable.profile_icon,
        R.string.profile,
        Screen.Profile.route
    )
}
