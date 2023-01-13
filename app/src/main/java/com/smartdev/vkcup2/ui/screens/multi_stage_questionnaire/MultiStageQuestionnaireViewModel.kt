package com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire

import androidx.lifecycle.ViewModel
import com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire.model.MultiStageAnswer
import com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire.model.MultiStageQuestionnaireUiState
import com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire.model.Question
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MultiStageQuestionnaireViewModel : ViewModel() {

    private val _multiStageQuestionnaireUiState = MutableStateFlow(MultiStageQuestionnaireUiState())
    val multiStageQuestionnaireUiState = _multiStageQuestionnaireUiState.asStateFlow()

    fun onClickNext() {
        with(_multiStageQuestionnaireUiState) {
            val targetQuestion = value.page + 1
            update { currentState ->
                currentState.copy(
                    page = if (targetQuestion >= value.questions.size) 0 else targetQuestion
                )
            }
        }
    }

    fun onClickBack() {
        with(_multiStageQuestionnaireUiState) {
            val targetQuestion = value.page - 1
            update { currentState ->
                currentState.copy(
                    page = if (targetQuestion <= -1) _multiStageQuestionnaireUiState.value.questions.size - 1 else targetQuestion
                )
            }
        }
    }

    fun selectAnswer(questionPos: Int, answerPos: Int) {
        with(_multiStageQuestionnaireUiState) {
            val currentQuestions = value.questions[questionPos]
            if (!currentQuestions.showResult) {

                val updateAnswers = getUpdateAnswers(
                    multiStageAnswers = currentQuestions.multiStageAnswers,
                    answerPos = answerPos,
                )

                val updateQuestion = getUpdateQuestion(
                    questions = value.questions,
                    questionPos = questionPos,
                    updateMultiStageAnswers = updateAnswers
                )

                update { currentState ->
                    currentState.copy(
                        questions = updateQuestion
                    )
                }
            }
        }
    }

    private fun getUpdateQuestion(
        questions: List<Question>,
        questionPos: Int,
        updateMultiStageAnswers: List<MultiStageAnswer>
    ) = questions
        .toMutableList()
        .let { currentQuestion ->
            val totalCount = currentQuestion[questionPos].totalVotes
            currentQuestion[questionPos] =
                currentQuestion[questionPos].copy(
                    showResult = true,
                    multiStageAnswers = updateMultiStageAnswers,
                    totalVotes = totalCount + 1
                )
            currentQuestion.toList()
        }


    private fun getUpdateAnswers(
        multiStageAnswers: List<MultiStageAnswer>,
        answerPos: Int,
    ) = multiStageAnswers
        .toMutableList()
        .let { answersMut ->
            val totalCount = answersMut[answerPos].selectedCount
            answersMut[answerPos] =
                answersMut[answerPos].copy(
                    isSelected = true,
                    selectedCount = totalCount + 1
                )
            answersMut.toList()
        }
}