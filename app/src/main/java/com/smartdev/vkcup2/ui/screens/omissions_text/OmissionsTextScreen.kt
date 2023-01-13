package com.smartdev.vkcup2.ui.screens.omissions_text

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.smartdev.vkcup2.common.AnimateContentSlider
import com.smartdev.vkcup2.common.TransitionButtons
import com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire.components.TopAppBarWithProgress
import com.smartdev.vkcup2.ui.screens.omissions_text.components.HyperlinkText
import com.smartdev.vkcup2.ui.theme.FillUnSelected
import com.smartdev.vkcup2.ui.theme.MainBackground
import com.smartdev.vkcup2.util.FlowCrossAxisAlignment
import com.smartdev.vkcup2.util.FlowRow
import com.smartdev.vkcup2.util.verticalSpace


@Composable
fun OmissionsTextScreen(
    modifier: Modifier = Modifier,
    viewModel: OmissionsTextViewModel,
    onBackClick: () -> Unit,
) {
    val uiState by viewModel.omissionsTextUiState.collectAsState()
    with(uiState) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MainBackground)
                .statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TopAppBarWithProgress(
                questionIndex = currentQuestion,
                totalQuestionsCount = questions.size,
                onBackClick = onBackClick
            )
            AnimateContentSlider(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 32.dp, horizontal = 16.dp),
                targetIndex = currentQuestion,
                contentSize = questions.size,
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    with(questions[it]) {
                        HyperlinkText(
                            modifier = Modifier.animateContentSize(),
                            fullText = text,
                            hyperLinks = hyperLinks,
                            onClickLick = { answer -> viewModel.deleteAnswer(answer) }
                        )
                        verticalSpace(height = 50.dp)
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            crossAxisAlignment = FlowCrossAxisAlignment.Start,
                            crossAxisSpacing = 6.dp,
                            mainAxisSpacing = 6.dp
                        ) {
                            answers.forEach { answer ->

                                val bgColor by animateColorAsState(
                                    targetValue = when (answer.isSelected) {
                                        true -> FillUnSelected
                                        false -> Color.White
                                    },
                                    animationSpec = tween(350)
                                )

                                val textColor by animateColorAsState(
                                    targetValue = when (answer.isSelected) {
                                        true -> Color.Transparent
                                        false -> Color.Black
                                    },
                                    animationSpec = tween(350)
                                )

                                Box(
                                    modifier = modifier
                                        .graphicsLayer(
                                            shape = MaterialTheme.shapes.large,
                                            clip = true
                                        )
                                        .background(bgColor)
                                        .clickable(
                                            enabled = !answer.isSelected,
                                            onClick = {
                                                viewModel.addAnswer(answer.text)
                                            }
                                        )
                                        .padding(vertical = 8.dp, horizontal = 12.dp)
                                ) {
                                    Text(
                                        text = answer.text,
                                        modifier = Modifier.align(Alignment.Center),
                                        color = textColor,
                                        style = MaterialTheme.typography.subtitle2
                                    )
                                }
                            }
                        }
                    }
                }
            }
            TransitionButtons(
                modifier = Modifier
                    .padding(16.dp),
                onClickNext = viewModel::onClickNext,
                onClickPrev = viewModel::onClickBack,
            )
        }
    }
}