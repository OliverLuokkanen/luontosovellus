package com.example.luontopeli.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Nature
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavRoutes(val route: String, val label: String, val icon: ImageVector) {
    object Map : NavRoutes("map", "Kartta", Icons.Filled.Map)
    object Camera : NavRoutes("camera", "Kamera", Icons.Filled.CameraAlt)
    object Discover : NavRoutes("discover", "Löydöt", Icons.Filled.Nature)
    object Stats : NavRoutes("stats", "Tilastot", Icons.Filled.BarChart)
}

val bottomNavItems = listOf(
    NavRoutes.Map,
    NavRoutes.Camera,
    NavRoutes.Discover,
    NavRoutes.Stats
)
