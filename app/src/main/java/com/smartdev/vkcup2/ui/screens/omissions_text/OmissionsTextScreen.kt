package com.smartdev.vkcup2.ui.screens.omissions_text

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.smartdev.vkcup2.R
import com.smartdev.vkcup2.ui.screens.choose.components.ChooseButton
import com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire.components.TopAppBarWithProgress
import com.smartdev.vkcup2.ui.screens.omissions_text.components.HyperlinkText
import com.smartdev.vkcup2.ui.theme.FillUnSelected
import com.smartdev.vkcup2.ui.theme.MainBackgroundColor
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
        val animatedProgress by animateFloatAsState(
            targetValue = (currentQuestion + 1) / questions.size.toFloat(),
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MainBackgroundColor)
                .statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column(modifier = Modifier.fillMaxWidth()) {
                TopAppBarWithProgress(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    questionIndex = currentQuestion + 1,
                    totalQuestionsCount = questions.size,
                    onBackClick = onBackClick
                )
                LinearProgressIndicator(
                    progress = animatedProgress,
                    modifier = Modifier
                        .fillMaxWidth(),
                    backgroundColor = FillUnSelected,
                    color = Color.White
                )
            }

            Crossfade(
                targetState = currentQuestion,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 32.dp, horizontal = 16.dp),
                animationSpec = tween(400)
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 32.dp),
                horizontalArrangement = Arrangement.spacedBy(50.dp)
            ) {

                ChooseButton(
                    modifier = Modifier.weight(1f),
                    backgroundColor = Color.White,
                    textColor = Color.Black,
                    text = stringResource(id = R.string.prev),
                    onClick = viewModel::onClickBack
                )

                ChooseButton(
                    modifier = Modifier
                        .weight(1f)
                        .animateContentSize(),
                    backgroundColor = Color.White,
                    textColor = Color.Black,
                    text = stringResource(id = R.string.next),
                    onClick = viewModel::onClickNext
                )
            }
        }
    }
}