package com.smartdev.vkcup2.common

import androidx.compose.material.MaterialTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.smartdev.vkcup2.R
import com.smartdev.vkcup2.ui.screens.choose.components.ChooseButton


@Composable
fun CheckButton(
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    onClick: () -> Unit
) {
    ChooseButton(
        modifier = modifier,
        backgroundColor = Color.White,
        indication = rememberRipple(color = Color.Black),
        shape = MaterialTheme.shapes.medium,
        text = stringResource(id = R.string.check),
        style = MaterialTheme.typography.button,
        enable = enable,
        textColor = Color.Black,
        onClick = onClick
    )
}