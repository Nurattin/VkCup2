package com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.smartdev.vkcup2.ui.theme.FillUnSelected

@Composable
fun TopAppBarWithProgress(
    modifier: Modifier = Modifier,
    questionIndex: Int,
    totalQuestionsCount: Int,
    onBackClick: () -> Unit,
) {
    val animatedProgress by animateFloatAsState(
        targetValue = (questionIndex + 1) / totalQuestionsCount.toFloat(),
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable(
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        indication = rememberRipple(),
                        onClick = onBackClick
                    ),
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = null,
                tint = Color.White
            )
            Text(
                text = "${questionIndex + 1}/${totalQuestionsCount}",
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.align(Alignment.Center),
            )
        }
        LinearProgressIndicator(
            progress = animatedProgress,
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = FillUnSelected,
            color = Color.White
        )
    }
}