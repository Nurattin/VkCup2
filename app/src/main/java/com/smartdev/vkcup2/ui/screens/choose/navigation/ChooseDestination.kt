package com.smartdev.vkcup2.ui.screens.choose.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.smartdev.vkcup2.R
import com.smartdev.vkcup2.base.VkCupNavigationDestination
import com.smartdev.vkcup2.ui.screens.choose.ChooseScreen
import com.smartdev.vkcup2.ui.screens.element_mapping.navigation.ElementMappingDestination
import com.smartdev.vkcup2.ui.screens.fill_omissions_text.navigation.FillOmissionTextDestination
import com.smartdev.vkcup2.ui.screens.omissions_text.navigation.OmissionsTextDestination
import com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire.navigation.MultiStageQuestionnaireDestination
import com.smartdev.vkcup2.ui.screens.rating.navigation.RatingDestination

object ChooseDestination : VkCupNavigationDestination {
    override val route = "choose_route"
}

fun NavGraphBuilder.choose(
    navigateTo: (route: String) -> Unit
) {
    composable(
        route = ChooseDestination.route,
    ) {
        ChooseScreen(
            onClickChooseBtn = {
                when (it) {
                    R.string.multi_stage_survey -> navigateTo(MultiStageQuestionnaireDestination.route)
                    R.string.element_mapping -> navigateTo(ElementMappingDestination.route)
                    R.string.drag_and_drop_options -> navigateTo(OmissionsTextDestination.route)
                    R.string.filling_in_the_gap -> navigateTo(FillOmissionTextDestination.route)
                    R.string.read_article_rating ->navigateTo(RatingDestination.route)
                }
            }
        )
    }
}