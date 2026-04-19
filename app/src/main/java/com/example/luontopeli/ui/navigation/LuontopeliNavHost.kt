package com.example.luontopeli.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.luontopeli.ui.screens.camera.CameraScreen
import com.example.luontopeli.ui.screens.discover.DiscoverScreen
import com.example.luontopeli.ui.screens.map.MapScreen
import com.example.luontopeli.ui.screens.stats.StatsScreen

@Composable
fun LuontopeliNavHost() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            NavigationBar {
                bottomNavItems.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.label) },
                        label = { Text(screen.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavRoutes.Map.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavRoutes.Map.route) { MapScreen() }
            composable(NavRoutes.Camera.route) { CameraScreen() }
            composable(NavRoutes.Discover.route) { DiscoverScreen() }
            composable(NavRoutes.Stats.route) { StatsScreen() }
        }
    }
}
