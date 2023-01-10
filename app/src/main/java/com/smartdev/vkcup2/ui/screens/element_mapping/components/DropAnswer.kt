package com.smartdev.vkcup2.ui.screens.element_mapping.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smartdev.vkcup2.ui.theme.FillUnSelected
import com.smartdev.vkcup2.util.DropTarget
import com.smartdev.vkcup2.util.shake


@Composable
fun DropAnswer(
    modifier: Modifier = Modifier,
    text: String,
    questionPos: Int,
    resultIsCorrect: Boolean? = null,
    onDeleteAnswer: (questionPos: Int, answer: String) -> Unit,
    onAddAnswer: (questionPos: Int, answer: String) -> Unit,
    onReplaceAnswer: (question: Int, answer: String) -> Unit,
) {

    DropTarget<String>(
        modifier = modifier
    ) { isInBound, item ->

        item?.let {
            if (isInBound)
                if (text.isNotEmpty()) onReplaceAnswer(questionPos, it)
                else onAddAnswer(questionPos, it)
        }

        val bgColorAnim by animateColorAsState(
            targetValue =
            if (isInBound) Color.White.copy(0.2f)
            else if (text.isEmpty()) FillUnSelected.copy(alpha = 0.1f)
            else Color.White,
            animationSpec = tween(400)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer(
                    shape = MaterialTheme.shapes.large,
                    clip = true,
                )
                .background(
                    when (resultIsCorrect) {
                        true -> Color.Green.copy(0.8f)
                        false -> Color.Red.copy(0.8f)
                        null -> null
                    } ?: bgColorAnim
                )
                .then(
                    if (text.isEmpty()) {
                        Modifier.border(
                            width = 1.dp,
                            color = Color.White.copy(0.1f),
                            shape = MaterialTheme.shapes.large
                        )
                    } else {
                        Modifier
                    }
                )
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                    enabled = resultIsCorrect == null,
                    onClick = {
                        if (text.isNotEmpty()) onDeleteAnswer(questionPos, text)
                    }
                )
                .padding(vertical = 8.dp, horizontal = 12.dp)
        ) {
            Text(
                text = text.ifEmpty { "" },
                modifier = Modifier.align(Alignment.Center),
                color = if (resultIsCorrect != null) Color.White else Color.Black,
                style = MaterialTheme.typography.subtitle2
            )
        }
    }
}


@Preview
@Composable
fun DropTargetFieldPreview(
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        DropAnswer(
            modifier = Modifier.fillMaxWidth(0.5f),
            text = "",
            onDeleteAnswer = { _, _ -> },
            onAddAnswer = { _, _ -> },
            onReplaceAnswer = { _, _ -> },
            questionPos = 1
        )
        DropAnswer(
            modifier = Modifier.fillMaxWidth(0.5f),
            text = "",
            onDeleteAnswer = { _, _ -> },
            onAddAnswer = { _, _ -> },
            onReplaceAnswer = { _, _ -> },
            questionPos = 1
        )
    }
}