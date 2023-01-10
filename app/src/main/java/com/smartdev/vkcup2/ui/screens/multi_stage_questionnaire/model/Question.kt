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
                question = "Сколько лет занимаетесь андроид разработкой",
                multiStageAnswers = listOf(
                    MultiStageAnswer(
                        "Хочу начать",
                        3764
                    ),
                    MultiStageAnswer(
                        "Менее полугода",
                        2313
                    ),
                    MultiStageAnswer(
                        "Уже год",
                        751
                    ),
                    MultiStageAnswer(
                        "От 1 до 3 лет",
                        221
                    ),
                    MultiStageAnswer(
                        "Более 3 лет",
                        82
                    ),
                )
            ),
            Question(
                question = "Какую архитектуру используете чаще всего",
                multiStageAnswers = listOf(
                    MultiStageAnswer(
                        "MVI",
                        123
                    ),
                    MultiStageAnswer(
                        "MVVM",
                        348
                    ),
                    MultiStageAnswer(
                        "MVP",
                        72
                    ),
                    MultiStageAnswer(
                        "MVC",
                        31
                    ),
                )
            ),
            Question(
                question = "Какой инструмент используете для построения UI",
                multiStageAnswers = listOf(
                    MultiStageAnswer(
                        "XML",
                        3425
                    ),
                    MultiStageAnswer(
                        "Compose",
                        874
                    ),
                )
            ),
            Question(
                question = "Какую библиотеку навигации используете в своем проекте",
                multiStageAnswers = listOf(
                    MultiStageAnswer(
                        "Navigation component",
                        1711
                    ),
                    MultiStageAnswer(
                        "Cicerone",
                        712
                    ),
                    MultiStageAnswer(
                        "Compose navigation",
                        72
                    ),
                    MultiStageAnswer(
                        "Odyssey",
                        22
                    ),
                    MultiStageAnswer(
                        "Самописное решение",
                        892
                    ),
                )
            )
        )
    }
}