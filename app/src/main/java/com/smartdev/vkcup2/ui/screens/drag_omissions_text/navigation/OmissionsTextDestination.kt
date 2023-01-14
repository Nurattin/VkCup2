package com.smartdev.vkcup2.ui.screens.drag_omissions_text.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.smartdev.vkcup2.base.VkCupNavigationDestination
import com.smartdev.vkcup2.ui.screens.drag_omissions_text.OmissionsTextScreen
import com.smartdev.vkcup2.ui.screens.drag_omissions_text.OmissionsTextViewModel

object OmissionsTextDestination : VkCupNavigationDestination {
    override val route = "omissions_text_route"
}

fun NavGraphBuilder.omissionsText(
    onBackClick: () -> Unit
) {

    composable(
        route = OmissionsTextDestination.route,
    ) {
        val viewModel: OmissionsTextViewModel = viewModel()

        OmissionsTextScreen(
            viewModel = viewModel,
            onBackClick = onBackClick
        )
    }
}