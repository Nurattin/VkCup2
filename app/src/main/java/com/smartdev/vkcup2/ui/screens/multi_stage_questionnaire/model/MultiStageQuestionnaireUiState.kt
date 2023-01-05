package com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire.model

import com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire.model.Question.Companion.listMock

data class MultiStageQuestionnaireUiState(
    val questions: List<Question> = listMock,
    val currentQuestion: Int = 0,
    val totalQuestion: Int = questions.size,
)