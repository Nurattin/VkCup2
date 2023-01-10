package com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.smartdev.vkcup2.base.VkCupNavigationDestination
import com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire.MultiStageQuestionnaireScreen
import com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire.MultiStageQuestionnaireViewModel

object MultiStageQuestionnaireDestination : VkCupNavigationDestination {
    override val route = "multi_stage_questionnaire_route"
}

fun NavGraphBuilder.multiStageQuestionnaire(
    onBackClick: () -> Unit
) {
    composable(
        route = MultiStageQuestionnaireDestination.route,
    ) {

        val viewModel: MultiStageQuestionnaireViewModel = viewModel()
        val uiState by viewModel.multiStageQuestionnaireUiState.collectAsState()

        MultiStageQuestionnaireScreen(
            uiState = uiState,
            onBackClick = onBackClick,
            onClickNext = viewModel::onClickNext,
            onClickBack = viewModel::onClickBack,
            selectAnswer = viewModel::selectAnswer
        )
    }
}