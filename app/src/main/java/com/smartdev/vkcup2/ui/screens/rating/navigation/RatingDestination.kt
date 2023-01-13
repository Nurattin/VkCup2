package com.smartdev.vkcup2.ui.screens.rating.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.smartdev.vkcup2.base.VkCupNavigationDestination
import com.smartdev.vkcup2.ui.screens.rating.RatingScreen
import com.smartdev.vkcup2.ui.screens.rating.RatingViewModel

object RatingDestination : VkCupNavigationDestination {
    override val route = "rating_route"
}

fun NavGraphBuilder.rating(
    onBackClick: () -> Unit
) {

    composable(
        route = RatingDestination.route,
    ) {

        val viewModel: RatingViewModel = viewModel()
        val uiState by viewModel.ratingUiState.collectAsState()

        RatingScreen(
            onBackClick = onBackClick,
            uiState = uiState,
            ratingArticle = viewModel::ratingArticle,
            onClickPrev = viewModel::onClickBack,
            onClickNext = viewModel::onClickNext,
            addComment = viewModel::addComment,
        )
    }
}