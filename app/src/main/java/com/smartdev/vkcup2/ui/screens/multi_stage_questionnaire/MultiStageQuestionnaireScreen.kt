package com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smartdev.vkcup2.R
import com.smartdev.vkcup2.ui.screens.choose.components.ChooseButton
import com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire.components.MultiStageQuestionnaireTobAppBar
import com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire.components.QuestionField
import com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire.model.MultiStageQuestionnaireUiState
import com.smartdev.vkcup2.ui.theme.FillUnSelected
import com.smartdev.vkcup2.ui.theme.MainBackgroundColor
import com.smartdev.vkcup2.util.verticalSpace

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MultiStageQuestionnaireScreen(
    modifier: Modifier = Modifier,
    uiState: MultiStageQuestionnaireUiState,
    selectAnswer: (currentQuestion: Int, currentAnswer: Int, shouldRemove: Boolean) -> Unit,
    onClickNext: () -> Unit,
    onClickBack: () -> Unit,
    onBackClick: () -> Unit
) {

    with(uiState) {
        val animatedProgress by animateFloatAsState(
            targetValue = (currentQuestion + 1) / totalQuestion.toFloat(),
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
        )
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = MainBackgroundColor)
                .statusBarsPadding(),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                MultiStageQuestionnaireTobAppBar(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    questionIndex = currentQuestion + 1,
                    totalQuestionsCount = totalQuestion,
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

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp, horizontal = 16.dp)
            ) {

                val question = questions[currentQuestion]

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = question.question,
                    color = Color.White,
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center
                )
                verticalSpace(height = 50.dp)
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    question.answers.forEachIndexed { index, answer ->
                        QuestionField(
                            question = answer.text,
                            showCounter = question.showResult,
                            isSelected = answer.isSelected,
                            count = answer.selectedCount,
                            totalCount = question.totalVotes,
                            onLongClick = {
                                if (answer.isSelected) selectAnswer(
                                    currentQuestion,
                                    index,
                                    true
                                )
                            },
                            onClick = { selectAnswer(currentQuestion, index, false) }
                        )
                    }
                }
                verticalSpace(height = 18.dp)
                Text(
                    modifier = Modifier.align(CenterHorizontally),
                    text = " Проголосовало ${question.totalVotes} человек",
                    style = MaterialTheme.typography.subtitle2,
                    color = Color.White
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 32.dp),
                horizontalArrangement = Arrangement.spacedBy(50.dp)
            ) {
                if (currentQuestion + 1 != 1) {
                    ChooseButton(
                        modifier = Modifier.weight(1f),
                        backgroundColor = Color.White,
                        textColor = Color.Black,
                        text = stringResource(id = R.string.prev),
                        onClick = onClickBack
                    )
                }
                if (currentQuestion + 1 != totalQuestion) {
                    ChooseButton(
                        modifier = Modifier
                            .weight(1f)
                            .animateContentSize(),
                        backgroundColor = Color.White,
                        textColor = Color.Black,
                        text = stringResource(id = R.string.next),
                        onClick = onClickNext
                    )
                } else {
                    ChooseButton(
                        modifier = Modifier
                            .weight(1f)
                            .animateContentSize(),
                        backgroundColor = Color.White,
                        textColor = Color.Black,
                        text = stringResource(id = R.string.finish),
                        onClick = onClickNext
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MultiStageQuestionnaireScreenPreview() {
    MultiStageQuestionnaireScreen(
        uiState = MultiStageQuestionnaireUiState(),
        onBackClick = {},
        onClickNext = {},
        selectAnswer = { _, _, _ -> },
        onClickBack = {}
    )
}