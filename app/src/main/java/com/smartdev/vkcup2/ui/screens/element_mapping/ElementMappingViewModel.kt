package com.smartdev.vkcup2.ui.screens.element_mapping

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ElementMappingViewModel() : ViewModel() {

    private val _elementMappingUiState = MutableStateFlow(ElementMappingUiState())
    val elementMappingUiState = _elementMappingUiState.asStateFlow()

    fun onClickNext() {
        with(_elementMappingUiState) {
            val targetQuestion = value.currentQuestion + 1
            update { currentState ->
                currentState.copy(
                    currentQuestion = if (targetQuestion >= value.questions.size) 0 else targetQuestion
                )
            }
        }
    }

    fun onClickBack() {
        with(_elementMappingUiState) {
            val targetQuestion = value.currentQuestion - 1
            update { currentState ->
                currentState.copy(
                    currentQuestion = if (targetQuestion <= -1) _elementMappingUiState.value.questions.size - 1 else targetQuestion
                )
            }
        }
    }

    fun checkAnswer() {
        _elementMappingUiState.update { currentState ->
            currentState.copy(
                questions = currentState.questions.toMutableList().let {
                    it[currentState.currentQuestion] =
                        it[currentState.currentQuestion].copy(showResult = true)
                    it.toList()
                },
            )
        }
    }

    fun addSelectedAnswer(questionPos: Int, answer: String) {
        with(_elementMappingUiState) {
            val currentQuestion = value.questions[value.currentQuestion].questions[questionPos]
            if (currentQuestion.selectedAnswer != answer) {
                val updateQuestion = value.questions
                    .toMutableList()
                    .let { outQ ->
                        outQ[value.currentQuestion] = outQ[value.currentQuestion].copy(
                            questions = outQ[value.currentQuestion].questions
                                .toMutableList()
                                .let { inQ ->
                                    inQ[questionPos] =
                                        inQ[questionPos].copy(selectedAnswer = answer)
                                    inQ.toList()
                                },
                            answerOptions = outQ[value.currentQuestion].answerOptions
                                .map { if (it.text == answer) it.copy(isSelected = true) else it }
                        )
                        outQ.toList()
                    }
                update { currentState ->
                    currentState.copy(questions = updateQuestion)
                }
            }
        }
    }

    fun deleteSelectedAnswer(questionPos: Int, answer: String) {
        with(_elementMappingUiState) {
            val updateQuestion = value.questions
                .toMutableList()
                .let { outQ ->
                    outQ[value.currentQuestion] = outQ[value.currentQuestion].copy(
                        questions = outQ[value.currentQuestion].questions
                            .toMutableList()
                            .let { inQ ->
                                inQ[questionPos] =
                                    inQ[questionPos].copy(selectedAnswer = "")
                                inQ.toList()
                            },
                        answerOptions = outQ[value.currentQuestion].answerOptions
                            .map { if (it.text == answer) it.copy(isSelected = false) else it }
                    )
                    outQ.toList()
                }
            update { currentState ->
                currentState.copy(questions = updateQuestion)
            }
        }
    }

    fun replaceSelectedAnswer(questionPos: Int, answer: String) {
        with(_elementMappingUiState) {

            val replaceAnswer: String

            val updateQuestion = value.questions
                .toMutableList()
                .let { outQ ->
                    outQ[value.currentQuestion] = outQ[value.currentQuestion].copy(
                        questions = outQ[value.currentQuestion].questions
                            .toMutableList()
                            .let { inQ ->
                                replaceAnswer = inQ[questionPos].selectedAnswer
                                inQ[questionPos] =
                                    inQ[questionPos].copy(selectedAnswer = answer)
                                inQ.toList()
                            },
                        answerOptions = outQ[value.currentQuestion].answerOptions
                            .map {
                                when (it.text) {
                                    replaceAnswer -> it.copy(isSelected = false)
                                    answer -> it.copy(isSelected = true)
                                    else -> it
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


data class ElementMappingUiState(
    val questions: List<ElementMappingQuestions> = ElementMappingQuestions.mockDate,
    val currentQuestion: Int = 0,
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