package com.smartdev.vkcup2.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController


@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
): AppState {
    return remember(navController) { AppState(navController) }
}

@Stable
class AppState constructor(
    val navController: NavHostController,
) {

    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    fun navigate(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
        }
    }

    fun onBackClick() {
        navController.popBackStack()
    }
}