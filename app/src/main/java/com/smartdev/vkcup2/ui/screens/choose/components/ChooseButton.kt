package com.smartdev.vkcup2.ui.screens.choose.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Indication
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.smartdev.vkcup2.R
import com.smartdev.vkcup2.ui.theme.FillUnSelected
import com.smartdev.vkcup2.ui.theme.UnEnableBackground
import com.smartdev.vkcup2.ui.theme.UnEnableText


val listChoose = listOf(
    R.string.multi_stage_survey_main,
    R.string.element_mapping_main,
    R.string.drag_and_drop_options,
    R.string.filling_in_the_gap_main,
    R.string.read_article_rating,
)

@Composable
fun ChooseButton(
    modifier: Modifier = Modifier,
    text: String,
    enable: Boolean = true,
    backgroundColor: Color = FillUnSelected,
    shape: Shape = MaterialTheme.shapes.small,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    indication: Indication? = rememberRipple(color = Color.White),
    style: TextStyle = MaterialTheme.typography.subtitle2,
    textColor: Color = Color.White,
    onClick: () -> Unit,
) {
    val bgColorAnim by animateColorAsState(
        targetValue = when (enable) {
            true -> backgroundColor
            false -> UnEnableBackground
        }
    )
    val textColorAnim by animateColorAsState(
        targetValue = when (enable) {
            true -> textColor
            false -> UnEnableText
        }
    )
    Box(
        modifier = modifier
            .clip(shape)
            .background(bgColorAnim)
            .clickable(
                interactionSource = interactionSource,
                indication = indication,
                enabled = enable,
                onClick = onClick
            )
            .padding(vertical = 14.dp, horizontal = 12.dp),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            text = text,
            style = style,
            color = textColorAnim,
            maxLines = 1,
        )
    }
}