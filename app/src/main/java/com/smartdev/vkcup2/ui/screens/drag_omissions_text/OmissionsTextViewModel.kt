package com.smartdev.vkcup2.ui.screens.drag_omissions_text

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.smartdev.vkcup2.ui.screens.element_mapping.Answer
import com.smartdev.vkcup2.ui.screens.element_mapping.components.ActionType
import com.smartdev.vkcup2.ui.screens.fill_omissions_text.Gap
import com.smartdev.vkcup2.util.getNextElementOrFirst
import com.smartdev.vkcup2.util.getPrevElementOrLast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OmissionsTextViewModel : ViewModel() {

    private val _omissionsTextUiState = MutableStateFlow(
        DragTextUiState()
    )
    val omissionsTextUiState = _omissionsTextUiState.asStateFlow()

    fun onClickNext() {
        _omissionsTextUiState.update { currentState ->
            currentState.copy(
                page = currentState.questions.getNextElementOrFirst(currentState.page)
            )
        }
    }

    fun onClickBack() {
        _omissionsTextUiState.update { currentState ->
            currentState.copy(
                page = currentState.questions.getPrevElementOrLast(currentState.page)
            )
        }
    }

    fun checkResult() {
        _omissionsTextUiState.update { currentState ->
            val updateQuestions = currentState.questions
                .toMutableList()
                .let { questions ->
                    val currentQuestion = questions[currentState.page]
                    questions[currentState.page] =
                        currentQuestion.copy(showResult = true)
                    questions.toList()
                }
            currentState.copy(
                questions = updateQuestions
            )
        }
    }

    fun changeAnswerSelection(answer: String, actionType: ActionType, currentAnswer: String) {
        with(_omissionsTextUiState) {
            val updateQuestion = value.questions
                .toMutableList()
                .let { questions ->
                    val currentQuestion = questions[value.page]
                    questions[value.page] = currentQuestion.copy(
                        answerOptions = when (actionType) {
                            ActionType.Add -> currentQuestion.answerOptions
                                .map { if (it.text == answer) it.copy(isSelected = true) else it }
                            ActionType.Delete -> currentQuestion.answerOptions
                                .map { if (it.text == answer) it.copy(isSelected = false) else it }
                            ActionType.Replace -> currentQuestion.answerOptions
                                .map {
                                    when (it.text) {
                                        currentAnswer -> it.copy(isSelected = false)
                                        answer -> it.copy(isSelected = true)
                                        else -> it
                                    }
                                }
                        }
                    )
                    questions.toList()
                }
            update { currentState ->
                currentState.copy(
                    questions = updateQuestion
                )
            }
        }
    }
}

data class DragTextUiState(
    val questions: List<DragTextQuestions> = DragTextQuestions.mockDate
        .map { ques ->
            ques.copy(listGap = ques.listGap.map { answer ->
                answer.copy(currentValue = mutableStateOf(""))
            })
        },
    val page: Int = 0,
)

data class DragTextQuestions(
    val text: String,
    val answerOptions: List<Answer>,
    val listGap: List<Gap>,
    val maxLengthAnswer: Int = answerOptions.maxOf { it.text.length },
    val showResult: Boolean = false,
) {
    companion object {
        val mockDate = listOf(
            DragTextQuestions(
                text = "Jetpack Compose это способ построения $placeholder , он построен на основе составных $placeholder . Эти функции позволяют" +
                        "вам определять пользовательский интерфейс вашего приложения," +
                        "описывая, как он должен выглядеть. Чтобы создать составную функцию, просто " +
                        "добавьте аннотацию $placeholder к имени $placeholder , для предпросмотра составных элементов используй аннотацию $placeholder " +
                        ". В модели Compose нет единственного родителя, на которого мы составляем композицию, и это решает проблему," +
                        " с которой мы столкнулись с моделью $placeholder . Compose работает с помощью $placeholder компилятора Kotlin на этапах проверки типов и" +
                        " генерации кода Kotlin: для использования compose не требуется процессор аннотаций. @Component аннотация больше похожа на ключевое слово языка." +
                        " Хорошей аналогией является ключевое слово Kotlin $placeholder .",
                listGap = listOf(
                    Gap(
                        currentValue = mutableStateOf(""),
                        id = 1,
                        rightValue = "ui"
                    ),
                    Gap(
                        currentValue = mutableStateOf(""),
                        id = 2,
                        rightValue = "функций"
                    ),
                    Gap(
                        currentValue = mutableStateOf(""),
                        id = 3,
                        rightValue = "@Composable"
                    ),
                    Gap(
                        currentValue = mutableStateOf(""),
                        id = 4,
                        rightValue = "функции"
                    ),
                    Gap(
                        currentValue = mutableStateOf(""),
                        id = 5,
                        rightValue = "@Preview"
                    ),
                    Gap(
                        currentValue = mutableStateOf(""),
                        id = 6,
                        rightValue = "наследования"
                    ),
                    Gap(
                        currentValue = mutableStateOf(""),
                        id = 7,
                        rightValue = "плагина"
                    ),
                    Gap(
                        currentValue = mutableStateOf(""),
                        id = 8,
                        rightValue = "suspend"
                    )
                ),
                answerOptions = listOf(
                    Answer("функций"),
                    Answer("ui"),
                    Answer("функции"),
                    Answer("@Preview"),
                    Answer("@Composable"),
                    Answer("плагина"),
                    Answer("suspend"),
                    Answer("наследования"),
                ),
            ),
            DragTextQuestions(
                text = "Классы часто требуют ссылок на другие классы. Например, Car классу может " +
                        "потребоваться ссылка на Engine класс. Эти требуемые классы называются " +
                        "$placeholder , и в этом примере Car класс зависит от наличия экземпляра" +
                        " Engine класса для запуска, один из способов получить необходимый ему объект: " +
                        "$placeholder зависимостей! Как выглядит код с внедрением зависимостей? Вместо" +
                        " того, чтобы каждый экземпляр Car создавал свой собственный Engine $placeholder" +
                        " при инициализации, он получает Engine объект в качестве параметра в своем $placeholder . " +
                        " Альтернативой внедрению зависимостей является использование $placeholder , в андроид проектах" +
                        " чаще всего используют библиотеку $placeholder для внедрения зависимостей.",
                listGap = listOf(
                    Gap(
                        currentValue = mutableStateOf(""),
                        id = 1,
                        rightValue = "зависимостями"
                    ),
                    Gap(
                        currentValue = mutableStateOf(""),
                        id = 2,
                        rightValue = "внедрение"
                    ),
                    Gap(
                        currentValue = mutableStateOf(""),
                        id = 3,
                        rightValue = "объект"
                    ),
                    Gap(
                        currentValue = mutableStateOf(""),
                        id = 4,
                        rightValue = "конструкторе"
                    ),
                    Gap(
                        currentValue = mutableStateOf(""),
                        id = 5,
                        rightValue = "локатора служб"
                    ),
                    Gap(
                        currentValue = mutableStateOf(""),
                        id = 6,
                        rightValue = "Dagger"
                    ),
                ),
                answerOptions = listOf(
                    Answer("внедрение"),
                    Answer("зависимостями"),
                    Answer("конструкторе"),
                    Answer("объект"),
                    Answer("Dagger"),
                    Answer("локатора служб"),
                ),
            ),
            DragTextQuestions(
                text = "Когда вы определяете класс, вы указываете свойства и методы, которые" +
                        " должны иметь все объекты этого класса. Определение класса начинается с" +
                        " $placeholder ключевого слова, Вы можете выбрать любое имя класса, которое" +
                        " хотите, но не используйте $placeholder слова Kotlin в качестве имени класса," +
                        " например $placeholder . Имя класса записывается в $placeholder , поэтому каждое" +
                        " слово начинается с заглавной буквы и между словами нет пробелов. Например," +
                        " в S mart D evice первая буква каждого слова пишется с заглавной буквы," +
                        " а между словами нет пробела. " +
                        "$placeholder .Переменные, которые определяют атрибуты объектов класса. " +
                        "$placeholder , которые содержат поведение и действия класса. " +
                        "$placeholder . Специальная функция-член, которая создает экземпляры класса во всей программе, в которой он определен.",
                listGap = listOf(
                    Gap(
                        currentValue = mutableStateOf(""),
                        id = 1,
                        rightValue = "class"
                    ),
                    Gap(
                        currentValue = mutableStateOf(""),
                        id = 2,
                        rightValue = "ключевые"
                    ),
                    Gap(
                        currentValue = mutableStateOf(""),
                        id = 3,
                        rightValue = "fun"
                    ),
                    Gap(
                        currentValue = mutableStateOf(""),
                        id = 4,
                        rightValue = "PascalCase"
                    ),
                    Gap(
                        currentValue = mutableStateOf(""),
                        id = 5,
                        rightValue = "Свойства"
                    ),
                    Gap(
                        currentValue = mutableStateOf(""),
                        id = 6,
                        rightValue = "Методы"
                    ),
                    Gap(
                        currentValue = mutableStateOf(""),
                        id = 7,
                        rightValue = "Конструкторы"
                    ),
                ),
                answerOptions = listOf(
                    Answer("Конструкторы"),
                    Answer("PascalCase"),
                    Answer("Методы"),
                    Answer("Свойства"),
                    Answer("fun"),
                    Answer("ключевые"),
                    Answer("class"),
                ),
            )
        )
    }
}

const val placeholder = "_____"

