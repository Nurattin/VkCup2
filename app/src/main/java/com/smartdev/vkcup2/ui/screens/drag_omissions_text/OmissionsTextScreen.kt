package com.smartdev.vkcup2.ui.screens.drag_omissions_text

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.sp
import com.smartdev.vkcup2.R
import com.smartdev.vkcup2.common.AnimateContentSlider
import com.smartdev.vkcup2.common.CheckButton
import com.smartdev.vkcup2.common.TransitionButtons
import com.smartdev.vkcup2.ui.screens.element_mapping.components.ActionType
import com.smartdev.vkcup2.ui.screens.element_mapping.components.DragAnswer
import com.smartdev.vkcup2.ui.screens.element_mapping.components.DropAnswerGapText
import com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire.components.TopAppBarWithProgress
import com.smartdev.vkcup2.ui.theme.MainBackground
import com.smartdev.vkcup2.util.FlowCrossAxisAlignment
import com.smartdev.vkcup2.util.FlowRow
import com.smartdev.vkcup2.util.LongPressDraggable
import com.smartdev.vkcup2.util.verticalSpace


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun OmissionsTextScreen(
    modifier: Modifier = Modifier,
    viewModel: OmissionsTextViewModel,
    onBackClick: () -> Unit,
) {
    val uiState by viewModel.omissionsTextUiState.collectAsState()
    with(uiState) {
        LongPressDraggable(modifier = modifier.fillMaxSize()) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(MainBackground)
                    .statusBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                TopAppBarWithProgress(
                    questionIndex = page,
                    totalQuestionsCount = questions.size,
                    onBackClick = onBackClick
                )
                AnimateContentSlider(
                    modifier = Modifier.weight(1f),
                    targetIndex = page,
                    contentSize = questions.size,
                ) { questionIndex ->
                    val scrollState = rememberScrollState()
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = dimensionResource(id = R.dimen.container_small)),
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.container_small))
                    ) {
                        with(questions[questionIndex]) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .verticalScroll(scrollState),
                                verticalArrangement = Arrangement.Center
                            ) {
                                val textV = buildAnnotatedString {
                                    var currentPlaceholder = 1
                                    text.split(" ").forEach { word ->
                                        if (word == placeholder) {
                                            appendInlineContent(
                                                currentPlaceholder.toString(),
                                                placeholder
                                            )
                                            append(AnnotatedString(text = " "))
                                            currentPlaceholder += 1
                                        } else {
                                            append(AnnotatedString(text = "$word "))
                                        }
                                    }
                                }
                                val inlineContent = listGap.associate { gap ->
                                    gap.id.toString() to InlineTextContent(
                                        Placeholder(
                                            width = (maxLengthAnswer * 11).sp,
                                            height = 24.sp,
                                            placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                                        )
                                    ) {
                                        DropAnswerGapText(
                                            text = gap.currentValue.value,
                                            changeAnswer = { answer, actionType, currentAnswer ->
                                                viewModel.changeAnswerSelection(
                                                    answer,
                                                    actionType,
                                                    currentAnswer
                                                )
                                                if (actionType == ActionType.Delete) {
                                                    gap.currentValue.value = ""
                                                } else {
                                                    gap.currentValue.value = answer
                                                }
                                            },
                                            questionPos = gap.id,
                                            resultIsCorrect = when (showResult) {
                                                true -> gap.currentValue.value == gap.rightValue
                                                false -> null
                                            }
                                        )
                                    }
                                }
                                verticalSpace(height = dimensionResource(id = R.dimen.container_medium))
                                BasicText(
                                    text = textV,
                                    inlineContent = inlineContent,
                                    style = MaterialTheme.typography.body1
                                )
                            }
                            FlowRow(
                                modifier = Modifier.fillMaxWidth(),
                                crossAxisAlignment = FlowCrossAxisAlignment.Start,
                                crossAxisSpacing = dimensionResource(id = R.dimen.cross_axis_spacing_small),
                                mainAxisSpacing = dimensionResource(id = R.dimen.main_axis_spacing_small)
                            ) {
                                answerOptions.forEach { answer ->
                                    DragAnswer(
                                        text = answer.text,
                                        canDrag = !answer.isSelected,
                                        resultIsShow = false
                                    )
                                }
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.container_small)),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.container_small)),
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = dimensionResource(id = R.dimen.container_small))
                            .align(Alignment.CenterHorizontally),
                        text = stringResource(id = R.string.drag_answer),
                        style = MaterialTheme.typography.caption
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.space_between_transition_btn)),
                    ) {
                        val currentQuestion = questions[page]
                        AnimatedContent(targetState = currentQuestion.showResult) { shouldShowCheckBtn ->
                            when (shouldShowCheckBtn) {
                                true -> TransitionButtons(
                                    onClickNext = viewModel::onClickNext,
                                    onClickPrev = viewModel::onClickBack,
                                )
                                false -> {
                                    val currentAnswerOptions = currentQuestion.answerOptions
                                    val checkIsEnable = remember(currentAnswerOptions) {
                                        currentAnswerOptions.all { ans -> ans.isSelected }
                                    }
                                    CheckButton(
                                        enable = checkIsEnable,
                                        onClick = viewModel::checkResult,
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