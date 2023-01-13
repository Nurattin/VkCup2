package com.smartdev.vkcup2.ui.screens.fill_omissions_text.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.smartdev.vkcup2.base.VkCupNavigationDestination
import com.smartdev.vkcup2.ui.screens.fill_omissions_text.FillOmissionsTextScreen

object FillOmissionTextDestination : VkCupNavigationDestination {
    override val route = "fill_omission_text_route"
}

fun NavGraphBuilder.fillOmissionText(
    onBackClick: () -> Unit
) {
    composable(
        route = FillOmissionTextDestination.route,
    ) {

        FillOmissionsTextScreen(
            onBackClick = onBackClick
        )
    }
}