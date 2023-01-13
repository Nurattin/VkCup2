package com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.smartdev.vkcup2.R
import com.smartdev.vkcup2.common.AnimateContentSlider
import com.smartdev.vkcup2.common.TransitionButtons
import com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire.components.AnswerField
import com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire.components.TopAppBarWithProgress
import com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire.model.MultiStageQuestionnaireUiState
import com.smartdev.vkcup2.ui.theme.MainBackground
import com.smartdev.vkcup2.util.verticalSpace

@Composable
fun MultiStageQuestionnaireScreen(
    modifier: Modifier = Modifier,
    uiState: MultiStageQuestionnaireUiState,
    selectAnswer: (currentQuestion: Int, currentAnswer: Int) -> Unit,
    onClickNext: () -> Unit,
    onClickPrev: () -> Unit,
    onBackClick: () -> Unit
) {
    with(uiState) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = MainBackground)
                .statusBarsPadding(),
            horizontalAlignment = CenterHorizontally,
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
            ) {
                val question = questions[it]
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(
                            vertical = dimensionResource(id = R.dimen.container_small),
                            horizontal = dimensionResource(id = R.dimen.container_small)
                        ),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = question.question,
                        style = MaterialTheme.typography.h6,
                        textAlign = TextAlign.Center
                    )
                    verticalSpace(height = 50.dp)
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        question.multiStageAnswers.forEachIndexed { index, answer ->
                            AnswerField(
                                answer = answer.text,
                                showResult = question.showResult,
                                isSelected = answer.isSelected,
                                count = answer.selectedCount,
                                totalCount = question.totalVotes,
                                onClick = { selectAnswer(page, index) },
                                isCorrect = answer.isCorrect
                            )
                        }
                    }
                    verticalSpace(height = 18.dp)
                    Text(
                        modifier = Modifier.align(CenterHorizontally),
                        text = stringResource(R.string.question_count, question.totalVotes),
                        style = MaterialTheme.typography.subtitle2,
                    )
                }
            }
            Column(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.container_small)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.container_small)),
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(id = R.dimen.container_small))
                        .align(CenterHorizontally),
                    text = stringResource(id = R.string.multi_stage_survey_hint),
                    style = MaterialTheme.typography.caption
                )
                TransitionButtons(
                    onClickNext = onClickNext,
                    onClickPrev = onClickPrev,
                )
            }
        }
    }
}