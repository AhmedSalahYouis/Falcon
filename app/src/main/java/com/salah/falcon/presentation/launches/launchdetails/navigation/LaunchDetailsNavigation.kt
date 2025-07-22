package com.salah.falcon.presentation.launches.launchdetails.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.salah.falcon.presentation.launches.launchdetails.LaunchDetailsScreen
import kotlinx.serialization.Serializable

@Serializable
data class LaunchDetailsRoute(val launchId: String) // Route to LaunchDetails screen

@Serializable
data object LaunchDetailsBaseRoute // Base route for LaunchDetails graph

fun NavGraphBuilder.launchDetailsScreenGraph(
    navigateBack: () -> Unit,
) {
    navigation<LaunchDetailsBaseRoute>(startDestination = LaunchDetailsRoute(launchId = "{launchId}")) {
        composable<LaunchDetailsRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<LaunchDetailsRoute>()
            LaunchDetailsScreen(
                launchId = route.launchId,
                navigateBack = navigateBack
            )
        }
    }
}
