package com.smartdev.vkcup2.ui.screens.choose.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smartdev.vkcup2.R


@Composable
fun ChooseTopAppBar(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
        ,
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_help_outline),
            contentDescription = null,
            tint = Color.White
        )
    }
}

@Preview
@Composable
fun ChooseTopAppBarPreview() {
    ChooseTopAppBar()
}