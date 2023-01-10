package com.smartdev.vkcup2.ui.screens.element_mapping.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.smartdev.vkcup2.ui.theme.FillUnSelected
import com.smartdev.vkcup2.util.DragTarget


@Composable
fun DragAnswer(
    modifier: Modifier = Modifier,
    canDrag: Boolean = true,
    resultIsShow: Boolean = false,
    text: String,
) {

    val bgColor by animateColorAsState(
        targetValue = when (canDrag) {
            true -> Color.White
            false -> FillUnSelected
        },
        animationSpec = tween(350)
    )

    val textColor by animateColorAsState(
        targetValue = when (canDrag) {
            true -> Color.Black
            false -> Color.Transparent
        },
        animationSpec = tween(350)
    )

    DragTarget(
        modifier = Modifier,
        dataToDrop = text,
        canDrag = canDrag && !resultIsShow,
        content = {
            Box(
                modifier = modifier
                    .graphicsLayer(
                        shape = MaterialTheme.shapes.large,
                        clip = true
                    )
                    .background(bgColor)
                    .padding(vertical = 8.dp, horizontal = 12.dp)
            ) {
                Text(
                    text = text,
                    modifier = Modifier.align(Alignment.Center),
                    color = textColor,
                    style = MaterialTheme.typography.subtitle2
                )
            }
        }
    )
}