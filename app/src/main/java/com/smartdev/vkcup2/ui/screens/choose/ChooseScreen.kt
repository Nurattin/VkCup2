package com.smartdev.vkcup2.ui.screens.choose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smartdev.vkcup2.R
import com.smartdev.vkcup2.ui.screens.choose.components.ChooseButton
import com.smartdev.vkcup2.ui.screens.choose.components.ChooseTopAppBar
import com.smartdev.vkcup2.ui.screens.choose.components.listChoose
import com.smartdev.vkcup2.ui.theme.MainBackground
import com.smartdev.vkcup2.util.verticalSpace


@Composable
fun ChooseScreen(
    modifier: Modifier = Modifier,
    onClickChooseBtn: (Int) -> Unit
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MainBackground)
            .statusBarsPadding()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp),
        horizontalAlignment = CenterHorizontally
    ) {
        ChooseTopAppBar()
        verticalSpace(height = 30.dp)
        Image(
            modifier = Modifier
                .size(250.dp),
            painter = painterResource(id = R.drawable.vk_header),
            contentDescription = null
        )
        verticalSpace(height = 26.dp)
        Text(
            text = "Выберите формат",
            style = MaterialTheme.typography.h6,
            color = Color.White
        )
        verticalSpace(height = 32.dp)
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            listChoose.forEach { item ->
                ChooseButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = item),
                    onClick = {
                        onClickChooseBtn(item)
                    }
                )
            }
        }
        verticalSpace(height = 32.dp)
    }
}

@Preview
@Composable
fun ChooseScreenPreview() {
    ChooseScreen(
        onClickChooseBtn = {}
    )
}

