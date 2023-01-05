package com.smartdev.vkcup2.ui.screens.choose.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.smartdev.vkcup2.R
import com.smartdev.vkcup2.base.VkCupNavigationDestination
import com.smartdev.vkcup2.ui.screens.choose.ChooseScreen
import com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire.navigation.MultiStageQuestionnaireDestination

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
                when(it){
                    R.string.multi_stage_survey -> navigateTo(MultiStageQuestionnaireDestination.route)
                }
            }
        )
    }
}