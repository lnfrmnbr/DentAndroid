package com.example.dentapp.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.dentapp.R
import com.example.dentapp.ui.theme.CadetBlue
import com.example.dentapp.ui.theme.DarkCyan
import com.example.dentapp.ui.theme.LightCyan

@Composable
fun NavBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home, BottomNavItem.PerioChart, BottomNavItem.Patients
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(CadetBlue)
            .padding(8.dp)
    ) {

        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(24.dp)),
            containerColor = LightCyan,
        ) {
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry?.destination?.route

            items.forEach { navItem ->
                NavigationBarItem(
                    selected = currentRoute == navItem.route, onClick = {
                        navController.navigate(navItem.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }, icon = {
                        Icon(
                            imageVector = navItem.icon,
                            contentDescription = stringResource(id = navItem.titleRes),
                            modifier = Modifier.size(24.dp)
                        )
                    }, colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = DarkCyan,
                        selectedTextColor = DarkCyan,
                        indicatorColor = DarkCyan.copy(alpha = 0.2f)
                    )
                )
            }
        }
    }
}

sealed class BottomNavItem(
    val icon: ImageVector,
    @StringRes val titleRes: Int,
    val route: String
) {
    data object Home : BottomNavItem(Icons.Default.Home, R.string.home, Screen.Home.route)
    data object Patients : BottomNavItem(Icons.Default.People, R.string.patients, Screen.Patients.route)
    data object PerioChart : BottomNavItem(Icons.Default.AddChart, R.string.home, Screen.PerioChart.route)
}
