package com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TopAppBarWithProgress(
    modifier: Modifier = Modifier,
    questionIndex: Int,
    totalQuestionsCount: Int,
    onBackClick: () -> Unit,
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = rememberRipple(),
                    onClick = onBackClick
                ),
            imageVector = Icons.Rounded.ArrowBack,
            contentDescription = null,
            tint = Color.White
        )

        Text(
            text = "${questionIndex}/${totalQuestionsCount}",
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.align(Alignment.Center),
            color = Color.White
        )
    }
}

@Preview
@Composable
fun MultiStageQuestionnaireTobAppBarPreview() {
    TopAppBarWithProgress(
        questionIndex = 3,
        totalQuestionsCount = 16,
        onBackClick = {}
    )
}