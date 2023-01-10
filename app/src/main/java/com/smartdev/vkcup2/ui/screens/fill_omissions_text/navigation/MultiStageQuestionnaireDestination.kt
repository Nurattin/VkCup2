package com.smartdev.vkcup2.ui.screens.fill_omissions_text.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.smartdev.vkcup2.base.VkCupNavigationDestination
import com.smartdev.vkcup2.ui.screens.fill_omissions_text.FillOmissionsText
import com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire.MultiStageQuestionnaireViewModel
import com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire.navigation.MultiStageQuestionnaireDestination

object FillOmissionTextDestination : VkCupNavigationDestination {
    override val route = "fill_omission_text_route"
}

fun NavGraphBuilder.fillOmissionText(
    onBackClick: () -> Unit
) {
    composable(
        route = FillOmissionTextDestination.route,
    ) {

//        val viewModel: MultiStageQuestionnaireViewModel = viewModel()
//        val uiState by viewModel.multiStageQuestionnaireUiState.collectAsState()

        FillOmissionsText(
        )
    }
}