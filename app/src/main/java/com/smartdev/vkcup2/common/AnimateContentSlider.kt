package com.smartdev.vkcup2.common

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.smartdev.vkcup2.util.AnimateDuration


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimateContentSlider(
    modifier: Modifier = Modifier,
    targetIndex: Int,
    contentSize: Int,
    content: @Composable() AnimatedVisibilityScope.(targetState: Int) -> Unit
) {
    AnimatedContent(
        modifier = modifier
            .fillMaxWidth(),
        targetState = targetIndex,
        transitionSpec = {
            val direction =
                when {
                    (targetState == 0 && initialState == contentSize - 1) -> AnimatedContentScope.SlideDirection.Left
                    (targetState == contentSize - 1 && initialState == 0) -> AnimatedContentScope.SlideDirection.Right
                    (initialState < targetState) -> AnimatedContentScope.SlideDirection.Left
                    else -> AnimatedContentScope.SlideDirection.Right
                }
            slideIntoContainer(
                towards = direction,
                animationSpec = tween(AnimateDuration.Long)
            ) with slideOutOfContainer(
                towards = direction,
                animationSpec = tween(AnimateDuration.Long)
            )
        },
        content = content
    )
}