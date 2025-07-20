package com.salah.falcon.presentation.launches.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.salah.falcon.presentation.launches.listing.LaunchesScreen
import kotlinx.serialization.Serializable

@Serializable data object LaunchesRoute // route to Launches screen

@Serializable data object LaunchesBaseRoute // route to base navigation graph

fun NavGraphBuilder.launchesScreenGraph(
) {
    navigation<LaunchesBaseRoute>(startDestination = LaunchesRoute) {
        composable<LaunchesRoute> {
            LaunchesScreen()
        }
    }
}
