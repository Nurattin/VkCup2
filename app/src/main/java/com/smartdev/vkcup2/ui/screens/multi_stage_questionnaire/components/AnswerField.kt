package com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.smartdev.vkcup2.R
import com.smartdev.vkcup2.ui.theme.Correct
import com.smartdev.vkcup2.ui.theme.Error
import com.smartdev.vkcup2.ui.theme.FillSelected
import com.smartdev.vkcup2.ui.theme.FillUnSelected
import com.smartdev.vkcup2.util.AnimateDuration
import com.smartdev.vkcup2.util.AnimateEasing.EaseOutBack
import com.smartdev.vkcup2.util.horizontalSpacer
import kotlin.math.roundToInt

@Composable
fun AnswerField(
    modifier: Modifier = Modifier,
    answer: String,
    showResult: Boolean,
    isSelected: Boolean,
    isCorrect: Boolean,
    count: Int,
    totalCount: Int,
    onClick: () -> Unit
) {
    val percentageSelected = remember(count, totalCount) {
        ((count / totalCount.toFloat()) * 100).roundToInt()
    }
    val animatedProgress by animateFloatAsState(
        targetValue = if (!showResult) 0f else percentageSelected / 100f,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )

    val indicatorColor = when {
        isCorrect && isSelected -> Correct
        !isCorrect && isSelected -> Error
        else -> FillSelected
    }

    val icon = when {
        isSelected && !isCorrect -> Icons.Rounded.Close
        isCorrect -> Icons.Rounded.Check
        else -> null
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .background(FillUnSelected)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = onClick,
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        LinearProgressIndicator(
            progress = animatedProgress,
            modifier = Modifier.matchParentSize(),
            backgroundColor = Color.Transparent,
            color = indicatorColor
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = dimensionResource(id = R.dimen.btn_medium),
                    horizontal = dimensionResource(id = R.dimen.btn_small)
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(0.8f),
                text = "$answer ${if (showResult) " - $count" else ""}",
                style = MaterialTheme.typography.subtitle2,
                overflow = TextOverflow.Ellipsis,
            )
            AnimatedVisibility(
                modifier = Modifier.weight(1f),
                visible = showResult,
                enter = slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(
                        durationMillis = AnimateDuration.ExtraLong,
                        easing = EaseOutBack
                    )
                ),
                exit = ExitTransition.None,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    if (icon != null) {
                        Icon(
                            modifier = Modifier.size(14.dp),
                            imageVector = icon,
                            contentDescription = null,
                            tint = Color.White
                        )
                        horizontalSpacer(width = 8.dp)
                    }
                    Text(
                        modifier = Modifier,
                        text = stringResource(
                            id = R.string.percentage_selected,
                            percentageSelected
                        ),
                        style = MaterialTheme.typography.subtitle2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}