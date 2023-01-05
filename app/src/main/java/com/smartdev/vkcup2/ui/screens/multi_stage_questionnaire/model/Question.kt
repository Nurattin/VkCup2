package com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire.model

data class Question(
    val showResult: Boolean = false,
    val question: String,
    val answers: List<Answer>,
    val totalVotes: Int = answers.sumOf { it.selectedCount },
) {
    companion object {
        val listMock = listOf(
            Question(
                question = "Сколько лет занимаетесь андроид разработкой",
                answers = listOf(
                    Answer(
                        "Хочу начать",
                        3764
                    ),
                    Answer(
                        "Менее полугода",
                        2313
                    ),
                    Answer(
                        "Уже год",
                        751
                    ),
                    Answer(
                        "От 1 до 3 лет",
                        221
                    ),
                    Answer(
                        "Более 3 лет",
                        82
                    ),
                )
            ),
            Question(
                question = "Какую архитектуру используете чаще всего",
                answers = listOf(
                    Answer(
                        "MVI",
                        123
                    ),
                    Answer(
                        "MVVM",
                        348
                    ),
                    Answer(
                        "MVP",
                        72
                    ),
                    Answer(
                        "MVC",
                        31
                    ),
                )
            ),
            Question(
                question = "Какой инструмент используете для построения UI",
                answers = listOf(
                    Answer(
                        "XML",
                        3425
                    ),
                    Answer(
                        "Compose",
                        874
                    ),
                )
            ),
            Question(
                question = "Какую библиотеку навигации используете в своем проекте",
                answers = listOf(
                    Answer(
                        "Navigation component",
                        1711
                    ),
                    Answer(
                        "Cicerone",
                        712
                    ),
                    Answer(
                        "Compose navigation",
                        72
                    ),
                    Answer(
                        "Odyssey",
                        22
                    ),
                    Answer(
                        "Самописное решение",
                        892
                    ),
                )
            )
        )
    }
}