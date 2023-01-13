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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import com.smartdev.vkcup2.R
import com.smartdev.vkcup2.ui.theme.UnEnableBackground
import com.smartdev.vkcup2.util.AnimateDuration
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
            false -> UnEnableBackground
        },
        animationSpec = tween(AnimateDuration.Long)
    )
    val textColor by animateColorAsState(
        targetValue = when (canDrag) {
            true -> Color.Black
            false -> Color.Transparent
        },
        animationSpec = tween(AnimateDuration.Long)
    )
    DragTarget(
        modifier = Modifier,
        dataToDrop = text,
        canDrag = canDrag && !resultIsShow
    ) {
        Box(
            modifier = modifier
                .clip(MaterialTheme.shapes.large)
                .background(bgColor)
                .padding(
                    vertical = dimensionResource(id = R.dimen.chip_small),
                    horizontal = dimensionResource(id = R.dimen.chip_medium),
                )
        ) {
            Text(
                text = text,
                modifier = Modifier.align(Alignment.Center),
                color = textColor,
                style = MaterialTheme.typography.subtitle1
            )
        }
    }
}