package com.smartdev.vkcup2.ui.screens.element_mapping

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.smartdev.vkcup2.R
import com.smartdev.vkcup2.ui.screens.choose.components.ChooseButton
import com.smartdev.vkcup2.ui.screens.element_mapping.components.DragAnswer
import com.smartdev.vkcup2.ui.screens.element_mapping.components.DropAnswer
import com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire.components.TopAppBarWithProgress
import com.smartdev.vkcup2.ui.theme.FillUnSelected
import com.smartdev.vkcup2.ui.theme.MainBackgroundColor
import com.smartdev.vkcup2.util.FlowCrossAxisAlignment
import com.smartdev.vkcup2.util.FlowRow
import com.smartdev.vkcup2.util.LongPressDraggable
import com.smartdev.vkcup2.util.verticalSpace

@Composable
fun ElementMappingScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModel: ElementMappingViewModel
) {
    val uiState by viewModel.elementMappingUiState.collectAsState()

    with(uiState) {
        val animatedProgress by animateFloatAsState(
            targetValue = (currentQuestion + 1) / questions.size.toFloat(),
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
        )
        LongPressDraggable(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(color = MainBackgroundColor)
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
                    ElementMapping(
                        elementMappingQuestions = questions[it],
                        onReplaceAnswer = viewModel::replaceSelectedAnswer,
                        onDeleteAnswer = viewModel::deleteSelectedAnswer,
                        onAddAnswer = viewModel::addSelectedAnswer,
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 32.dp),
                    horizontalArrangement = Arrangement.spacedBy(50.dp)
                ) {

                    when (questions[currentQuestion].showResult) {
                        true -> {
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
                        false -> {
                            val currentQuestions = questions[currentQuestion].questions
                            val checkIsEnable by remember(currentQuestions) {
                                mutableStateOf(currentQuestions.all { it.selectedAnswer != "" })
                            }
                            ChooseButton(
                                modifier = Modifier.weight(1f),
                                backgroundColor = Color.White,
                                enable = checkIsEnable,
                                textColor = Color.Black,
                                shape = MaterialTheme.shapes.medium,
                                text = stringResource(id = R.string.check),
                                onClick = viewModel::checkAnswer
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ElementMapping(
    modifier: Modifier = Modifier,
    elementMappingQuestions: ElementMappingQuestions,
    onDeleteAnswer: (questionPos: Int, answer: String) -> Unit,
    onAddAnswer: (questionPos: Int, answer: String) -> Unit,
    onReplaceAnswer: (question: Int, answer: String) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center
        ) {
            elementMappingQuestions.questions.forEachIndexed { index, elementMappingQuestion ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(28.dp)
                ) {
                    Text(
                        modifier = Modifier.weight(2f),
                        text = elementMappingQuestion.question,
                        style = MaterialTheme.typography.h6,
                        color = Color.White
                    )
                    DropAnswer(
                        modifier = Modifier.weight(1f),
                        text = elementMappingQuestion.selectedAnswer,
                        onAddAnswer = onAddAnswer,
                        onDeleteAnswer = onDeleteAnswer,
                        onReplaceAnswer = onReplaceAnswer,
                        questionPos = index,
                        resultIsCorrect = if (elementMappingQuestions.showResult) {
                            elementMappingQuestion.selectedAnswer == elementMappingQuestion.rightAnswer
                        } else null
                    )
                }
                verticalSpace(height = 12.dp)
            }
        }
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            crossAxisAlignment = FlowCrossAxisAlignment.Start,
            crossAxisSpacing = 6.dp,
            mainAxisSpacing = 6.dp
        ) {
            elementMappingQuestions.answerOptions.forEach { answer ->
                DragAnswer(
                    text = answer.text,
                    canDrag = !answer.isSelected,
                    resultIsShow = elementMappingQuestions.showResult
                )
            }
        }
    }
}
