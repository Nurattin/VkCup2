package com.smartdev.vkcup2.ui.screens.fill_omissions_text

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.smartdev.vkcup2.ui.screens.drag_omissions_text.placeholder
import com.smartdev.vkcup2.util.getNextElementOrFirst
import com.smartdev.vkcup2.util.getPrevElementOrLast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FillOmissionsTextViewModel : ViewModel() {

    private val listGap = PassTextState.mock.map { ques ->
        ques.copy(listGap = ques.listGap.map { answer ->
            answer.copy(currentValue = mutableStateOf(""))
        })
    }
    private val _fillOmissionsText = MutableStateFlow(FillOmissionsText(questions = listGap))
    val fillOmissionsText = _fillOmissionsText.asStateFlow()

    fun onClickNext() {
        _fillOmissionsText.update { currentState ->
            currentState.copy(
                page = currentState.questions.getNextElementOrFirst(currentState.page)
            )
        }
    }

    fun onClickBack() {
        _fillOmissionsText.update { currentState ->
            currentState.copy(
                page = currentState.questions.getPrevElementOrLast(currentState.page)
            )
        }
    }

    fun checkResult() {
        _fillOmissionsText.update { currentValue ->
            val updateQuestions = currentValue.questions.toMutableList().let { questions ->
                questions[currentValue.page] = questions[currentValue.page].copy(
                    showResult = true
                )
                questions.toList()
            }
            currentValue.copy(questions = updateQuestions)
        }
    }
}

data class FillOmissionsText(
    val page: Int = 0,
    val questions: List<PassTextState>
)

data class PassTextState(
    val listGap: List<Gap>,
    val text: String,
    val showResult: Boolean = false
) {
    companion object {
        val mock = listOf(
            PassTextState(
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
                    ),
                ),
                text = "Jetpack Compose это способ построения $placeholder , он построен на основе составных $placeholder . Эти функции позволяют" +
                        "вам определять пользовательский интерфейс вашего приложения," +
                        "описывая, как он должен выглядеть. Чтобы создать составную функцию, просто" +
                        "добавьте аннотацию $placeholder к имени $placeholder , для предпросмотра составных элементов используй аннотацию $placeholder " +
                        ". В модели Compose нет единственного родителя, на которого мы составляем композицию, и это решает проблему," +
                        " с которой мы столкнулись с моделью $placeholder . Compose работает с помощью $placeholder компилятора Kotlin на этапах проверки типов и" +
                        " генерации кода Kotlin: для использования compose не требуется процессор аннотаций. @Component аннотация больше похожа на ключевое слово языка." +
                        " Хорошей аналогией является ключевое слово Kotlin $placeholder .",
            ),
            PassTextState(
                listGap
                = listOf(
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
                text = "Когда вы определяете класс, вы указываете свойства и методы, которые" +
                        " должны иметь все объекты этого класса. Определение класса начинается с" +
                        " $placeholder ключевого слова, Вы можете выбрать любое имя класса, которое" +
                        " хотите, но не используйте $placeholder слова Kotlin в качестве имени класса," +
                        " например $placeholder . Имя класса записывается в $placeholder , поэтому каждое" +
                        " слово начинается с заглавной буквы и между словами нет пробелов. Например," +
                        " в S mart D evice первая буква каждого слова пишется с заглавной буквы," +
                        " а между словами нет пробела. $placeholder .Переменные, которые определяют атрибуты объектов класса." +
                        " $placeholder , которые содержат поведение и действия класса." +
                        " $placeholder . Специальная функция-член, которая создает экземпляры класса во всей программе, в которой он определен.",
            ),
            PassTextState(
                listGap = listOf(
                    Gap(
                        currentValue = mutableStateOf(""),
                        id = 1,
                        rightValue = "статически"
                    ),
                    Gap(
                        currentValue = mutableStateOf(""),
                        id = 2,
                        rightValue = "объектно"
                    ),
                    Gap(
                        currentValue = mutableStateOf(""),
                        id = 3,
                        rightValue = "Java"
                    ),
                    Gap(
                        currentValue = mutableStateOf(""),
                        id = 4,
                        rightValue = "JetBrains"
                    ),
                    Gap(
                        currentValue = mutableStateOf(""),
                        id = 5,
                        rightValue = "main"
                    ),
                    Gap(
                        currentValue = mutableStateOf(""),
                        id = 6,
                        rightValue = "Null"
                    ),
                ),
                text = "Kotlin это $placeholder типизированный $placeholder -ориентированный" +
                        " язык программирования, работающий поверх $placeholder Virtual Machine" +
                        " и разрабатываемый компанией $placeholder . Точкой входа приложения Kotlin" +
                        " является $placeholder функция. Особенностью Kotlin которая вспоминается первой" +
                        ". это $placeholder safety",
            )
        )
    }
}

data class Gap(
    val id: Int,
    val currentValue: MutableState<String> = mutableStateOf(""),
    val rightValue: String,
    val isSelected: Boolean = false
)