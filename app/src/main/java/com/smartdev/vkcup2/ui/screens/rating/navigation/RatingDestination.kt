package com.smartdev.vkcup2.ui.screens.rating.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.smartdev.vkcup2.base.VkCupNavigationDestination
import com.smartdev.vkcup2.ui.screens.rating.RatingScreen

object RatingDestination : VkCupNavigationDestination {
    override val route = "rating_route"
}

fun NavGraphBuilder.rating(
    onBackClick: () -> Unit
) {

    composable(
        route = RatingDestination.route,
    ) {

        RatingScreen(
            onBackClick = onBackClick
        )
    }
}