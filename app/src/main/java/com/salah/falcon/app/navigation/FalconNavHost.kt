package com.salah.falcon.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.salah.falcon.app.FalconAppState
import com.salah.falcon.presentation.launches.navigation.LaunchesBaseRoute
import com.salah.falcon.presentation.launches.navigation.launchesScreenGraph

/**
 * The navigation graph defined in this file defines the different top level routes. Navigation
 * within each route is handled using state and Back Handlers.
 */
@Composable
fun FalconNavHost(
    appState: FalconAppState,
    modifier: Modifier = Modifier,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = LaunchesBaseRoute,
        modifier = modifier,
    ) {
        launchesScreenGraph()
    }
}
