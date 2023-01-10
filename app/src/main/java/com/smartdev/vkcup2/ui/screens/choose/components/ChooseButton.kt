package com.smartdev.vkcup2.ui.screens.choose.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smartdev.vkcup2.R
import com.smartdev.vkcup2.ui.theme.FillUnSelected


val listChoose = listOf(
    R.string.multi_stage_survey,
    R.string.element_mapping,
    R.string.drag_and_drop_options,
    R.string.filling_in_the_gap,
    R.string.read_article_rating,
)

@Composable
fun ChooseButton(
    modifier: Modifier = Modifier,
    text: String,
    enable: Boolean = true,
    backgroundColor: Color = FillUnSelected,
    shape: Shape = MaterialTheme.shapes.small,
    textColor: Color = Color.White,
    onClick: () -> Unit,
) {

    val bgColorAnim by animateColorAsState(
        targetValue = when (enable) {
            true -> backgroundColor
            false -> backgroundColor.copy(0.6f)
        }
    )

    Box(
        modifier = modifier
            .graphicsLayer(
                shape = shape,
                clip = true
            )
            .background(bgColorAnim)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = Color.White),
                enabled = enable,
                onClick = onClick
            )
            .padding(vertical = 14.dp, horizontal = 12.dp),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = text,
            style = MaterialTheme.typography.subtitle2,
            overflow = TextOverflow.Ellipsis,
            color = textColor,
            maxLines = 1,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun ChooseButtonPreview() {
    ChooseButton(
        modifier = Modifier,
        text = "Многоступенчатый опрос с вариантами ответов ",
        onClick = {},
    )
}