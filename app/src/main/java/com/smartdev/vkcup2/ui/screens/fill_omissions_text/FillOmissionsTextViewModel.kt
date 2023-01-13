package com.smartdev.vkcup2.ui.screens.fill_omissions_text

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.smartdev.vkcup2.ui.screens.omissions_text.placeholder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FillOmissionsTextViewModel : ViewModel() {

    private val listGap = PassTextState.mock.map { it }

    private val _fillOmissionsText = MutableStateFlow(FillOmissionsText(questions = listGap))
    val fillOmissionsText = _fillOmissionsText.asStateFlow()

    fun onClickNext() {
        with(_fillOmissionsText) {
            val targetQuestion = value.page + 1
            update { currentState ->
                currentState.copy(
                    page = if (targetQuestion >= value.questions.size) 0 else targetQuestion
                )
            }
        }
    }

    fun onClickBack() {
        with(_fillOmissionsText) {
            val targetQuestion = value.page - 1
            update { currentState ->
                currentState.copy(
                    page = if (targetQuestion <= -1) value.questions.size - 1 else targetQuestion
                )
            }
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
)