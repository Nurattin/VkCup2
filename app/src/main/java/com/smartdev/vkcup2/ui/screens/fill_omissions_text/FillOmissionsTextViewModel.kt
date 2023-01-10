package com.smartdev.vkcup2.ui.screens.fill_omissions_text

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.smartdev.vkcup2.ui.screens.choose.components.listChoose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FillOmissionsTextViewModel : ViewModel() {

    val listGap = PassTextState.mock

    private val _fillOmissionsText = MutableStateFlow(FillOmissionsText(questions = listGap))
    val fillOmissionsText = _fillOmissionsText.asStateFlow()

}

data class FillOmissionsText(
    val currentQuestion: Int = 0,
    val questions: List<PassTextState>
)

data class PassTextState(
    val listGap: List<Gap>,
    val text: String,
) {
    companion object {
        val mock = listOf(
            PassTextState(
                listGap = listOf(
                    Gap(
                        currentValue = mutableStateOf(""),
                        startIndex = 8,
                    ),
                    Gap(
                        currentValue = mutableStateOf(""),
                        startIndex = 18,
                    ),
                    Gap(
                        currentValue = mutableStateOf(""),
                        startIndex = 28,
                    ),
                ),
                text = "Jetpack  это способ построения ui, он построен на основе . Эти функции позволяют" +
                        "вам программно определять пользовательский интерфейс вашего приложения," +
                        "описывая, как он должен выглядеть. Чтобы создать составную функцию, просто" +
                        "добавьте аннотацию asdsad к имени функции.",
            )
        )
    }
}

data class Gap(
    val currentValue: MutableState<String> = mutableStateOf(""),
    val startIndex: Int,
    val isSelected: MutableState<Boolean> = mutableStateOf(false),
)