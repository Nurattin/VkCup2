package com.smartdev.vkcup2.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.smartdev.vkcup2.ui.screens.choose.navigation.choose
import com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire.navigation.multiStageQuestionnaire


@Composable
fun VkCupNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onNavigateToDestination: (route: String) -> Unit,
    onBackClick: () -> Unit,
    startDestination: String,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        choose(
            navigateTo = onNavigateToDestination
        )
        multiStageQuestionnaire(
            navigateTo = onNavigateToDestination,
            onBackClick = onBackClick
        )
    }
}