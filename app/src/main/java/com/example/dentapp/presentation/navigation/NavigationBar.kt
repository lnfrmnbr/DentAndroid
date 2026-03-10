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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.dentapp.R
import com.example.dentapp.ui.theme.LightBlue
import com.example.dentapp.ui.theme.LightGreyBlue
import com.example.dentapp.ui.theme.NewBlue

@Composable
fun NavBar(navController: NavController) {
    val items =
        listOf(BottomNavItem.Home, BottomNavItem.PerioChart, BottomNavItem.Patients)

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
                    navController.navigate(navItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Image(
                        painter = painterResource(id = navItem.iconRes),
                        contentDescription = stringResource(navItem.titleRes),
                        modifier = Modifier.size(20.dp),
                        colorFilter = if (currentRoute == navItem.route) {
                            ColorFilter.tint(NewBlue)
                        } else {
                            ColorFilter.tint(LightGreyBlue)
                        }
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NewBlue,
                    selectedTextColor = NewBlue,
                    indicatorColor = LightBlue
                ),
                label = {Text(stringResource(navItem.titleRes), color = LightGreyBlue)}
            )
        }
    }
}

sealed class BottomNavItem(@DrawableRes val iconRes: Int, @StringRes val titleRes: Int, val route: String) {
    data object Home : BottomNavItem(R.drawable.home_icon, R.string.home, Screen.Home.route)
    data object Patients :
        BottomNavItem(R.drawable.home_icon, R.string.patients, Screen.Patients.route)

    data object PerioChart :
        BottomNavItem(R.drawable.home_icon, R.string.home, Screen.PerioChart.route)
}
