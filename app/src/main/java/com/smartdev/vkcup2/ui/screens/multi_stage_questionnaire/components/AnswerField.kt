package com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartdev.vkcup2.ui.theme.FillSelected
import com.smartdev.vkcup2.ui.theme.FillUnSelected
import com.smartdev.vkcup2.util.horizontalSpacer
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnswerField(
    modifier: Modifier = Modifier,
    answer: String,
    showCounter: Boolean,
    isSelected: Boolean,
    count: Int,
    totalCount: Int,
    onLongClick: () -> Unit,
    onClick: () -> Unit
) {

    val percentageSelected = remember(count, totalCount) {
        ((count / totalCount.toFloat()) * 100).roundToInt()
    }
    val animatedProgress by animateFloatAsState(
        targetValue = if (!showCounter) 0f else percentageSelected / 100f,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )
    Box(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer(
                shape = MaterialTheme.shapes.small,
                clip = true
            )
            .background(FillUnSelected)
            .combinedClickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onLongClick = onLongClick,
                onClick = onClick,
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        LinearProgressIndicator(
            progress = animatedProgress,
            modifier = Modifier.matchParentSize(),
            backgroundColor = Color.Transparent,
            color = FillSelected
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            val text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                ) {
                    append(answer)
                }
                if (showCounter) {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.White.copy(alpha = 0.5f)
                        )
                    ) {
                        append(" - $count")
                    }
                }
            }
            Text(
                modifier = Modifier.fillMaxWidth(0.8f),
                text = text,
                style = MaterialTheme.typography.subtitle2,
                overflow = TextOverflow.Ellipsis,
                color = Color.White,
            )
            AnimatedVisibility(
                modifier = Modifier.weight(1f),
                visible = showCounter,
                enter = slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(durationMillis = 600, easing = EaseOutBack)
                ),
                exit = ExitTransition.None,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    if (isSelected) {
                        Icon(
                            modifier = Modifier.size(12.dp),
                            imageVector = Icons.Rounded.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                        horizontalSpacer(width = 8.dp)
                    }
                    Text(
                        modifier = Modifier,
                        text = "$percentageSelected %",
                        style = MaterialTheme.typography.subtitle2,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White,
                    )
                }
            }

        }
    }
}


@Preview
@Composable
fun QuestionFieldPreview() {

    var showCounter by remember {
        mutableStateOf(false)
    }

    var selectedIndex by remember {
        mutableStateOf(-1)
    }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AnswerField(
            answer = "MVVM",
            showCounter = showCounter,
            totalCount = 123,
            count = 103,
            isSelected = selectedIndex == 1,
            onClick = {
                showCounter = true
                selectedIndex = 1
            },
            onLongClick = {
                showCounter = false
                selectedIndex = -1
            }
        )
        AnswerField(
            answer = "MVI",
            showCounter = showCounter,
            totalCount = 123,
            count = 20,
            isSelected = selectedIndex == 2,
            onClick = {
                showCounter = true
                selectedIndex = 2
            },
            onLongClick = {
                showCounter = false
                selectedIndex = -1
            }
        )
    }
}