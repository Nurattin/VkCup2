package com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire.model

data class Answer(
    val text: String,
    val selectedCount: Int,
    val isSelected: Boolean = false,
)