package com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire.model

data class Question(
    val showResult: Boolean = false,
    val question: String,
    val multiStageAnswers: List<MultiStageAnswer>,
    val totalVotes: Int = multiStageAnswers.sumOf { it.selectedCount },
) {
    companion object {
        val listMock = listOf(
            Question(
                question = "Какое из следующих объявлений переменных является действительным?",
                multiStageAnswers = listOf(
                    MultiStageAnswer(
                        "var hello: Int? = \"\"",
                        320,
                    ),
                    MultiStageAnswer(
                        "String \"hello\" = hello",
                        82
                    ),
                    MultiStageAnswer(
                        "val hello = \"hello\"",
                        927,
                        isCorrect = true
                    ),
                    MultiStageAnswer(
                        "hello: String = \"hello\"",
                        420
                    ),
                )
            ),
            Question(
                question = "Какой из следующих типов данных не является типом данных в Kotlin?",
                multiStageAnswers = listOf(
                    MultiStageAnswer(
                        "String",
                        92
                    ),
                    MultiStageAnswer(
                        "Decimal",
                        500,
                        isCorrect = true
                    ),
                    MultiStageAnswer(
                        "Int",
                        72
                    ),
                    MultiStageAnswer(
                        "Boolean",
                        32
                    ),
                )
            ),
            Question(
                question = "В Kotlin точкой входа в программу является",
                multiStageAnswers = listOf(
                    MultiStageAnswer(
                        "println()",
                        24
                    ),
                    MultiStageAnswer(
                        "val",
                        10
                    ),
                    MultiStageAnswer(
                        "main()",
                        489,
                        isCorrect = true
                    ),
                    MultiStageAnswer(
                        "return",
                        82
                    ),
                    MultiStageAnswer(
                        "continue",
                        120
                    ),
                    MultiStageAnswer(
                        "start",
                        160
                    ),
                )
            ),
            Question(
                question = "Что такое Jetpack Compose?",
                multiStageAnswers = listOf(
                    MultiStageAnswer(
                        "Современный инструментарий для разработки пользовательского интерфейса Android",
                        1711,
                        isCorrect = true
                    ),
                    MultiStageAnswer(
                        "Инструментарий для разработки библиотек",
                        712
                    ),
                    MultiStageAnswer(
                        "Интерфейс базы данных",
                        423
                    ),
                    MultiStageAnswer(
                        "Плагин для сборки APK",
                        520
                    ),
                )
            ),
            Question(
                question = "Какая аннотация используется для аннотирования составной функции?",
                multiStageAnswers = listOf(
                    MultiStageAnswer(
                        "@Annotation",
                        120,
                    ),
                    MultiStageAnswer(
                        "@ComposableFunction",
                        712
                    ),
                    MultiStageAnswer(
                        "@Composable",
                        1200,
                        isCorrect = true
                    ),
                    MultiStageAnswer(
                        "@Preview",
                        732
                    ),
                    MultiStageAnswer(
                        "@Component",
                        400
                    ),
                    MultiStageAnswer(
                        "@Ui",
                        632
                    ),
                )
            )
        )
    }
}