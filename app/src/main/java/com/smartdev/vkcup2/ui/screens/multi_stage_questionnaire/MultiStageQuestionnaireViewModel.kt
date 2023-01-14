package com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire

import androidx.lifecycle.ViewModel
import com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire.model.MultiStageAnswer
import com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire.model.MultiStageQuestionnaireUiState
import com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire.model.Question
import com.smartdev.vkcup2.util.getNextElementOrFirst
import com.smartdev.vkcup2.util.getPrevElementOrLast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MultiStageQuestionnaireViewModel : ViewModel() {

    private val _multiStageQuestionnaireUiState = MutableStateFlow(MultiStageQuestionnaireUiState())
    val multiStageQuestionnaireUiState = _multiStageQuestionnaireUiState.asStateFlow()

    fun onClickNext() {
        _multiStageQuestionnaireUiState.update { currentState ->
            currentState.copy(
                page = currentState.questions.getNextElementOrFirst(currentState.page)
            )
        }
    }

    fun onClickBack() {
        _multiStageQuestionnaireUiState.update { currentState ->
            currentState.copy(
                page = currentState.questions.getPrevElementOrLast(currentState.page)
            )
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