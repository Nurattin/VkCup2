package com.smartdev.vkcup2.ui.screens.omissions_text

import androidx.lifecycle.ViewModel
import com.smartdev.vkcup2.ui.screens.element_mapping.Answer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OmissionsTextViewModel() : ViewModel() {

    private val _omissionsTextUiState = MutableStateFlow(OmissionsTextUiState())
    val omissionsTextUiState = _omissionsTextUiState.asStateFlow()

    fun onClickNext() {
        with(_omissionsTextUiState) {
            val targetQuestion = value.currentQuestion + 1
            update { currentState ->
                currentState.copy(
                    currentQuestion = if (targetQuestion >= value.questions.size) 0 else targetQuestion
                )
            }
        }
    }

    fun onClickBack() {
        with(_omissionsTextUiState) {
            val targetQuestion = value.currentQuestion - 1
            update { currentState ->
                currentState.copy(
                    currentQuestion = if (targetQuestion <= -1) _omissionsTextUiState.value.questions.size - 1 else targetQuestion
                )
            }
        }
    }

    fun addAnswer(answer: String) {
        with(_omissionsTextUiState.value) {
            if (questions[currentQuestion].text.contains(placeholder)) {
                val updateQuestions = questions.toMutableList().let {
                    it[currentQuestion] = it[currentQuestion].copy(
                        text = it[currentQuestion].text.replaceFirst(placeholder, answer, true),
                        answers = it[currentQuestion].answers.map { currentAnswer ->
                            if (answer == currentAnswer.text) currentAnswer.copy(isSelected = true) else currentAnswer
                        },
                        hyperLinks = it[currentQuestion].hyperLinks.plus(answer)
                    )
                    it.toList()
                }
                _omissionsTextUiState.update { currentState ->
                    currentState.copy(
                        questions = updateQuestions
                    )
                }
            }
        }
    }

    fun deleteAnswer(answer: String) {
        with(_omissionsTextUiState.value) {
            val updateQuestions = questions.toMutableList().let {
                it[currentQuestion] = it[currentQuestion].copy(
                    text = it[currentQuestion].text.replaceFirst(answer, placeholder, true),
                    answers = it[currentQuestion].answers.map { currentAnswer ->
                        if (answer == currentAnswer.text) currentAnswer.copy(isSelected = false) else currentAnswer
                    },
                    hyperLinks = it[currentQuestion].hyperLinks.minus(answer)
                )
                it.toList()
            }
            _omissionsTextUiState.update { currentState ->
                currentState.copy(
                    questions = updateQuestions
                )
            }
        }
    }
}

const val placeholder = "_____"


data class OmissionsTextUiState(
    val questions: List<OmissionsTextQuestion> = OmissionsTextQuestion.mock,
    val currentQuestion: Int = 0
)

data class OmissionsTextQuestion(
    val text: String,
    val answers: List<Answer>,
    val hyperLinks: List<String> = emptyList(),
){
    companion object {
        val mock = listOf(
            OmissionsTextQuestion(
                text = "Jetpack Compose это $placeholder способ построения ui, он построен на основе $placeholder. Эти функции позволяют" +
                        " вам программно определять пользовательский интерфейс вашего приложения," +
                        " описывая, как он должен выглядеть. Чтобы создать составную функцию, просто" +
                        " добавьте аннотацию $placeholder к имени функции.",
                answers = listOf(
                    Answer(
                        text = "составных функций",
                        isSelected = false
                    ),
                    Answer(
                        text = "описательных функций",
                        isSelected = false
                    ),
                    Answer(
                        text = "@Composable",
                        isSelected = false
                    ),
                    Answer(
                        text = "@Component",
                        isSelected = false
                    ),
                    Answer(
                        text = "дикларотивный",
                        isSelected = false
                    ),
                    Answer(
                        text = "императивный",
                        isSelected = false
                    )
                )
            ),
            OmissionsTextQuestion(
                text = "Сопрограмма - это шаблон проектирования $placeholder," +
                        " который вы можете использовать на Android для" +
                        " упрощения кода, выполняемого $placeholder. Сопрограммы" +
                        " были добавлены в Kotlin в версии $placeholder и основаны" +
                        " на устоявшихся концепциях из других языков. Можно думать о сопрограмме" +
                        " как о $placeholder. Подобно потокам, сопрограммы могут" +
                        " выполняться параллельно, ожидать друг друга и взаимодействовать. " +
                        "Запустить короутину можно используя билдеры сопрограмм одним из них " +
                        "является $placeholder",
                answers = listOf(
                    Answer(
                        text = "параллелизма",
                        isSelected = false
                    ),
                    Answer(
                        text = "асинхронно",
                        isSelected = false
                    ),
                    Answer(
                        text = "синхронно",
                        isSelected = false
                    ),
                    Answer(
                        text = "1.3",
                        isSelected = false
                    ),
                    Answer(
                        text = "4.5",
                        isSelected = false
                    ),
                    Answer(
                        text = "7.2",
                        isSelected = false
                    ),
                    Answer(
                        text = "легком потоке",
                        isSelected = false
                    ),
                    Answer(
                        text = "тяжелом потоке",
                        isSelected = false
                    ),
                    Answer(
                        text = "launch",
                        isSelected = false
                    ),
                    Answer(
                        text = "coroutineScope",
                        isSelected = false
                    ),
                    Answer(
                        text = "withContext",
                        isSelected = false
                    )
                )
            )
        )
    }
}



