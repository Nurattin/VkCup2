package com.smartdev.vkcup2.ui.screens.element_mapping

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.smartdev.vkcup2.R
import com.smartdev.vkcup2.common.AnimateContentSlider
import com.smartdev.vkcup2.common.CheckButton
import com.smartdev.vkcup2.common.TransitionButtons
import com.smartdev.vkcup2.ui.screens.element_mapping.components.ActionType
import com.smartdev.vkcup2.ui.screens.element_mapping.components.DragAnswer
import com.smartdev.vkcup2.ui.screens.element_mapping.components.DropAnswer
import com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire.components.TopAppBarWithProgress
import com.smartdev.vkcup2.ui.theme.MainBackground
import com.smartdev.vkcup2.util.FlowCrossAxisAlignment
import com.smartdev.vkcup2.util.FlowRow
import com.smartdev.vkcup2.util.LongPressDraggable
import com.smartdev.vkcup2.util.verticalSpace

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ElementMappingScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModel: ElementMappingViewModel
) {
    val uiState by viewModel.elementMappingUiState.collectAsState()
    with(uiState) {
        LongPressDraggable(modifier = modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .background(color = MainBackground),
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = spacedBy(dimensionResource(id = R.dimen.container_small))
                ) {
                    TopAppBarWithProgress(
                        questionIndex = page,
                        totalQuestionsCount = questions.size,
                        onBackClick = onBackClick
                    )
                    Text(
                        modifier = Modifier
                            .padding(dimensionResource(id = R.dimen.container_small))
                            .fillMaxWidth(),
                        text = stringResource(id = R.string.element_mapping),
                        style = MaterialTheme.typography.h6,
                        textAlign = TextAlign.Center
                    )
                }
                AnimateContentSlider(
                    modifier = Modifier.weight(1f),
                    targetIndex = page,
                    contentSize = questions.size,
                ) { questionIndex ->
                    ElementMapping(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                vertical = dimensionResource(id = R.dimen.container_small),
                                horizontal = dimensionResource(id = R.dimen.container_small)
                            ),
                        elementMappingQuestions = questions[questionIndex],
                        changeAnswer = viewModel::changeAnswer
                    )
                }
                Column(
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.container_small)),
                    verticalArrangement = spacedBy(dimensionResource(id = R.dimen.container_small)),
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = dimensionResource(id = R.dimen.container_small))
                            .align(CenterHorizontally),
                        text = stringResource(id = R.string.element_mapping_hint),
                        style = MaterialTheme.typography.caption
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = spacedBy(50.dp)
                    ) {
                        val currentQuestion = questions[page]
                        AnimatedContent(targetState = currentQuestion.showResult) { shouldShowCheckBtn ->
                            when (shouldShowCheckBtn) {
                                true -> TransitionButtons(
                                    onClickNext = viewModel::onClickNext,
                                    onClickPrev = viewModel::onClickBack,
                                )
                                false -> {
                                    val currentQuestions = currentQuestion.questions
                                    val checkIsEnable = remember(currentQuestions) {
                                        currentQuestions.all { it.selectedAnswer.isNotEmpty() }
                                    }
                                    CheckButton(
                                        enable = checkIsEnable,
                                        onClick = viewModel::checkAnswer,
                                    )
                                }
                            }
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
    changeAnswer: (questionPos: Int, answer: String, actionType: ActionType) -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier,
        verticalArrangement = spacedBy(dimensionResource(id = R.dimen.container_small))
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Center
        ) {
            elementMappingQuestions.questions.forEachIndexed { index, elementMappingQuestion ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = spacedBy(dimensionResource(id = R.dimen.container_small))
                ) {
                    Text(
                        modifier = Modifier.weight(2f),
                        text = elementMappingQuestion.question,
                        style = MaterialTheme.typography.h6,
                    )
                    DropAnswer(
                        modifier = Modifier.weight(1f),
                        text = elementMappingQuestion.selectedAnswer,
                        changeAnswer = changeAnswer,
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
