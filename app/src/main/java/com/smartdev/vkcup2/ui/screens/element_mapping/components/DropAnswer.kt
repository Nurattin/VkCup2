package com.smartdev.vkcup2.ui.screens.element_mapping.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.smartdev.vkcup2.R
import com.smartdev.vkcup2.ui.theme.Correct
import com.smartdev.vkcup2.ui.theme.Error
import com.smartdev.vkcup2.ui.theme.FillUnSelected
import com.smartdev.vkcup2.util.AnimateDuration
import com.smartdev.vkcup2.util.DropTarget

@Composable
fun DropAnswer(
    modifier: Modifier = Modifier,
    text: String,
    questionPos: Int,
    resultIsCorrect: Boolean? = null,
    changeAnswer: (questionPos: Int, answer: String, actionType: ActionType) -> Unit,
) {
    DropTarget<String>(
        modifier = modifier
    ) { isInBound, item ->
        item?.let {
            if (isInBound) changeAnswer(
                questionPos, it, if (text.isNotEmpty()) ActionType.Replace else ActionType.Add
            )
        }
        val animationDelay = remember(resultIsCorrect) {
            if (resultIsCorrect != null) questionPos * AnimateDuration.Fast else 0
        }
        val bgColorAnim by animateColorAsState(
            targetValue = when (resultIsCorrect) {
                true -> Correct
                false -> Error
                else -> when {
                    isInBound -> Color.White.copy(0.2f)
                    text.isEmpty() -> FillUnSelected.copy(0.1f)
                    else -> Color.White
                }
            },
            animationSpec = tween(
                durationMillis = AnimateDuration.Long,
                delayMillis = animationDelay
            )
        )

        val txColorAnim by animateColorAsState(
            targetValue = if (resultIsCorrect != null) Color.White else Color.Black,
            animationSpec = tween(
                durationMillis = AnimateDuration.Long,
                delayMillis = animationDelay
            )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.large)
                .background(bgColorAnim)
                .then(
                    if (text.isEmpty())
                        Modifier.border(
                            width = 1.dp,
                            color = Color.White.copy(0.1f),
                            shape = MaterialTheme.shapes.large
                        )
                    else Modifier
                )
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                    enabled = resultIsCorrect == null,
                    onClick = {
                        if (text.isNotEmpty()) changeAnswer(
                            questionPos, text, ActionType.Delete
                        )
                    }
                )
                .padding(
                    vertical = dimensionResource(id = R.dimen.chip_small),
                    horizontal = dimensionResource(id = R.dimen.chip_small),
                )
        ) {
            Text(
                text = text.ifEmpty { "" },
                modifier = Modifier.align(Alignment.Center),
                color = txColorAnim,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

enum class ActionType {
    Add, Delete, Replace
}

@Composable
fun DropAnswerGapText(
    modifier: Modifier = Modifier,
    text: String,
    questionPos: Int,
    resultIsCorrect: Boolean? = null,
    changeAnswer: (answer: String, actionType: ActionType, currentAnswer: String) -> Unit,
) {
    DropTarget<String>(
        modifier = modifier
    ) { isInBound, item ->
        item?.let { dragText ->
            if (isInBound) changeAnswer(
                dragText,
                when (text.isNotEmpty()) {
                    true -> ActionType.Replace
                    false -> ActionType.Add
                },
                text
            )
        }

        val animationDelay = remember(resultIsCorrect) {
            if (resultIsCorrect != null) questionPos * AnimateDuration.Fast else 0
        }

        val bgColorAnim by animateColorAsState(
            targetValue = when (resultIsCorrect) {
                true -> Correct
                false -> Error
                else -> when {
                    isInBound -> Color.White.copy(0.2f)
                    text.isEmpty() -> FillUnSelected.copy(0.1f)
                    else -> Color.White
                }
            },
            animationSpec = tween(
                durationMillis = AnimateDuration.Long,
                delayMillis = animationDelay
            )
        )

        val txColorAnim by animateColorAsState(
            targetValue = if (resultIsCorrect != null) Color.White else Color.Black,
            animationSpec = tween(
                durationMillis = AnimateDuration.Long,
                delayMillis = animationDelay
            )
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.small)
                .background(bgColorAnim)
                .then(
                    if (text.isEmpty())
                        Modifier.border(
                            width = 1.dp,
                            color = Color.White.copy(0.1f),
                            shape = MaterialTheme.shapes.small
                        )
                    else Modifier
                )
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(color = Color.Black),
                    enabled = resultIsCorrect == null,
                    onClick = {
                        if (text.isNotEmpty()) changeAnswer(text, ActionType.Delete, text)
                    }
                )
        ) {
            Text(
                text = text.ifEmpty { "" },
                modifier = Modifier.align(Alignment.Center),
                color = txColorAnim,
                style = MaterialTheme.typography.body2,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }
    }
}