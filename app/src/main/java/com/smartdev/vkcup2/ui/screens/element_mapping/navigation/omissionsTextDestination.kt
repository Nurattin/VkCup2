package com.smartdev.vkcup2.ui.screens.element_mapping.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.smartdev.vkcup2.base.VkCupNavigationDestination
import com.smartdev.vkcup2.ui.screens.element_mapping.ElementMappingScreen
import com.smartdev.vkcup2.ui.screens.element_mapping.ElementMappingViewModel

object ElementMappingDestination : VkCupNavigationDestination {
    override val route = "element_mapping_route"
}

fun NavGraphBuilder.elementMapping(
    onBackClick: () -> Unit
) {

    composable(
        route = ElementMappingDestination.route,
    ) {
        val viewModel: ElementMappingViewModel = viewModel()
        ElementMappingScreen(
            onBackClick = onBackClick,
            viewModel = viewModel
        )
    }
}