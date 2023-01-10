package com.smartdev.vkcup2.ui.screens.fill_omissions_text

import androidx.compose.foundation.layout.*
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.smartdev.vkcup2.ui.screens.omissions_text.components.HyperlinkText2

@Composable
fun FillOmissionsText(
    modifier: Modifier = Modifier,
    viewModel: FillOmissionsTextViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val uiState by viewModel.fillOmissionsText.collectAsState()

    with(uiState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            verticalArrangement = Arrangement.Center
        ) {

            questions[currentQuestion].run{

                HyperlinkText2(
                    modifier = Modifier,
                    fullText = text,
                    hyperLinks = listOf(8),
                    onClickLick = { _ -> focusRequester.requestFocus() }
                )
//
//                TextField(
//                    value = listGap[0],
//                    onValueChange = { listGap[0] = it },
//                    modifier = Modifier
//                        .size(0.dp)
//                        .focusTarget()
//                        .focusRequester(focusRequester)
//                )
            }
        }
    }
}