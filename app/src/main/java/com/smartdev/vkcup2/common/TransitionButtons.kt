package com.smartdev.vkcup2.common

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.smartdev.vkcup2.R
import com.smartdev.vkcup2.ui.screens.choose.components.ChooseButton


@Composable
fun TransitionButtons(
    modifier: Modifier = Modifier,
    onClickPrev: () -> Unit,
    onClickNext: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(50.dp)
    ) {
        TransitionButton.values().forEach { btn ->
            ChooseButton(
                modifier = Modifier.weight(1f),
                backgroundColor = Color.White,
                textColor = Color.Black,
                indication = rememberRipple(color = Color.Black),
                shape = MaterialTheme.shapes.medium,
                text = stringResource(id = btn.text),
                style = MaterialTheme.typography.button,
                onClick = when (btn) {
                    TransitionButton.Prev -> onClickPrev
                    TransitionButton.Next -> onClickNext
                }
            )
        }
    }
}

private enum class TransitionButton(@StringRes val text: Int) {
    Prev(text = R.string.prev), Next(text = R.string.next)
}