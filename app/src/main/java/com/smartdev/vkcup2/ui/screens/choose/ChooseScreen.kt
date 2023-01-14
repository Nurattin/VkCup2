package com.smartdev.vkcup2.ui.screens.choose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.smartdev.vkcup2.R
import com.smartdev.vkcup2.ui.screens.choose.components.ChooseButton
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
            .padding(dimensionResource(id = R.dimen.container_small)),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(150.dp),
            painter = painterResource(id = R.drawable.vk_header),
            contentDescription = null
        )
        verticalSpace(dimensionResource(id = R.dimen.container_small))
        Text(
            text = stringResource(id = R.string.choose_format),
            style = MaterialTheme.typography.h6,
        )
        verticalSpace(dimensionResource(id = R.dimen.container_medium))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            listChoose.forEach { item ->
                ChooseButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = item),
                    onClick = { onClickChooseBtn(item) }
                )
            }
        }
        verticalSpace(dimensionResource(id = R.dimen.container_medium))
    }
}

