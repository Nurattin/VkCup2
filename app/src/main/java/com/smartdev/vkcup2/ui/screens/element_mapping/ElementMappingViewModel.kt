package com.smartdev.vkcup2.ui.screens.element_mapping

import androidx.lifecycle.ViewModel
import com.smartdev.vkcup2.ui.screens.element_mapping.components.ActionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ElementMappingViewModel : ViewModel() {

    private val _elementMappingUiState = MutableStateFlow(ElementMappingUiState())
    val elementMappingUiState = _elementMappingUiState.asStateFlow()

    fun onClickNext() {
        with(_elementMappingUiState) {
            val targetQuestion = value.page + 1
            update { currentState ->
                currentState.copy(
                    page = if (targetQuestion >= value.questions.size) 0 else targetQuestion
                )
            }
        }
    }

    fun onClickBack() {
        with(_elementMappingUiState) {
            val targetQuestion = value.page - 1
            update { currentState ->
                currentState.copy(
                    page = if (targetQuestion <= -1) _elementMappingUiState.value.questions.size - 1 else targetQuestion
                )
            }
        }
    }

    fun checkAnswer() {
        _elementMappingUiState.update { currentState ->
            currentState.copy(
                questions = currentState.questions.toMutableList().let {
                    it[currentState.page] =
                        it[currentState.page].copy(showResult = true)
                    it.toList()
                },
            )
        }
    }

    fun changeAnswer(questionPos: Int, answer: String, actionType: ActionType) {
        with(_elementMappingUiState) {
            val currentQuestion = value.questions[value.page].questions[questionPos]
            if (currentQuestion.selectedAnswer != answer || actionType != ActionType.Add) {
                var replaceAnswer = ""
                val updateQuestion = value.questions
                    .toMutableList()
                    .let { outQ ->
                        val currentQuestionIn = outQ[value.page]
                        outQ[value.page] = currentQuestionIn.copy(
                            questions = currentQuestionIn.questions
                                .toMutableList()
                                .let { inQ ->
                                    val currentAnswer = inQ[questionPos]
                                    when (actionType) {
                                        ActionType.Add -> inQ[questionPos] =
                                            currentAnswer.copy(selectedAnswer = answer)
                                        ActionType.Delete -> inQ[questionPos] =
                                            currentAnswer.copy(selectedAnswer = "")
                                        ActionType.Replace -> {
                                            replaceAnswer = inQ[questionPos].selectedAnswer
                                            inQ[questionPos] =
                                                currentAnswer.copy(selectedAnswer = answer)
                                        }
                                    }
                                    inQ.toList()
                                },
                            answerOptions = when (actionType) {
                                ActionType.Add -> currentQuestionIn.answerOptions
                                    .map { if (it.text == answer) it.copy(isSelected = true) else it }
                                ActionType.Delete -> currentQuestionIn.answerOptions
                                    .map { if (it.text == answer) it.copy(isSelected = false) else it }
                                ActionType.Replace -> currentQuestionIn.answerOptions
                                    .map {
                                        when (it.text) {
                                            replaceAnswer -> it.copy(isSelected = false)
                                            answer -> it.copy(isSelected = true)
                                            else -> it
                                        }
                                    }
                            }
                        )
                        outQ.toList()
                    }
                update { currentState ->
                    currentState.copy(questions = updateQuestion)
                }
            }
        }
    }
}


data class ElementMappingUiState(
    val questions: List<ElementMappingQuestions> = ElementMappingQuestions.mockDate,
    val page: Int = 0,
    val resultIsCorrect: Boolean? = null

)

data class ElementMappingQuestions(
    val questions: List<ElementMappingQuestion>,
    val answerOptions: List<Answer>,
    val showResult: Boolean = false,
) {
    companion object {
        val mockDate = listOf(
            ElementMappingQuestions(
                questions = listOf(
                    ElementMappingQuestion(
                        question = "Строит  контейнер зависимостей",
                        rightAnswer = "Component",
                        selectedAnswer = ""
                    ),
                    ElementMappingQuestion(
                        question = "Используется для внедрения конструктора",
                        rightAnswer = "Inject",
                        selectedAnswer = ""
                    ),
                    ElementMappingQuestion(
                        question = "Обертка над dagger2",
                        rightAnswer = "Hilt",
                        selectedAnswer = ""
                    ),
                ),
                answerOptions = listOf(
                    Answer("BindInstance"),
                    Answer("Bind"),
                    Answer("Component"),
                    Answer("Inject"),
                    Answer("Hilt"),
                    Answer("Provide")
                ),
            ), ElementMappingQuestions(
                questions = listOf(
                    ElementMappingQuestion(
                        question = "функциями расширения для launch() и async()",
                        rightAnswer = "CoroutineScope",
                        selectedAnswer = ""
                    ),
                    ElementMappingQuestion(
                        question = "Процесс преобразования результата JSON в пригодные для использования данные, как это делается с помощью Gson",
                        rightAnswer = "Converting",
                        selectedAnswer = ""
                    ),
                    ElementMappingQuestion(
                        question = "Библиотека для работы с ассинхронностью",
                        rightAnswer = "Coroutine",
                        selectedAnswer = ""
                    ),
                ),
                answerOptions = listOf(
                    Answer("CoroutineScope"),
                    Answer("Job"),
                    Answer("Dispatcher"),
                    Answer("Inject"),
                    Answer("Converting"),
                    Answer("Coroutine")
                ),
            ), ElementMappingQuestions(
                questions = listOf(
                    ElementMappingQuestion(
                        question = "Строит  контейнер зависимостей",
                        rightAnswer = "Component",
                        selectedAnswer = ""
                    ),
                    ElementMappingQuestion(
                        question = "Используется для внедрения конструктора",
                        rightAnswer = "Inject",
                        selectedAnswer = ""
                    ),
                    ElementMappingQuestion(
                        question = "Обертка над dagger2",
                        rightAnswer = "Hilt",
                        selectedAnswer = ""
                    )
                ),
                answerOptions = listOf(
                    Answer("BindInstance"),
                    Answer("Bind"),
                    Answer("Component"),
                    Answer("Inject"),
                    Answer("Hilt"),
                    Answer("Provide")
                ),
            )
        )
    }
}

data class ElementMappingQuestion(
    val question: String,
    val rightAnswer: String,
    val selectedAnswer: String,
)

data class Answer(
    val text: String,
    val isSelected: Boolean = false
)