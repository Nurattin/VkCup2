package com.smartdev.vkcup2.ui.screens.fill_omissions_text

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.smartdev.vkcup2.R
import com.smartdev.vkcup2.common.AnimateContentSlider
import com.smartdev.vkcup2.common.CheckButton
import com.smartdev.vkcup2.common.TransitionButtons
import com.smartdev.vkcup2.ui.screens.multi_stage_questionnaire.components.TopAppBarWithProgress
import com.smartdev.vkcup2.ui.screens.drag_omissions_text.placeholder
import com.smartdev.vkcup2.ui.theme.Correct
import com.smartdev.vkcup2.ui.theme.Error
import com.smartdev.vkcup2.ui.theme.FillUnSelected
import com.smartdev.vkcup2.util.AnimateDuration

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FillOmissionsTextScreen(
    modifier: Modifier = Modifier,
    viewModel: FillOmissionsTextViewModel = viewModel(),
    onBackClick: () -> Unit,
) {
    val uiState by viewModel.fillOmissionsText.collectAsState()
    val focusManager = LocalFocusManager.current
    with(uiState) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .statusBarsPadding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.container_small))
            ) {
                TopAppBarWithProgress(
                    questionIndex = page,
                    totalQuestionsCount = questions.size,
                    onBackClick = onBackClick
                )
                Text(
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.container_small))
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.filling_in_the_gap),
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center
                )
            }
            AnimateContentSlider(
                modifier = Modifier.weight(1f),
                targetIndex = page,
                contentSize = questions.size,
            ) {

                val scrollState = rememberScrollState()
                val currentQuestion = questions[it]

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.Center
                ) {
                    with(currentQuestion) {
                        var currentPlaceholder = 1
                        val textV = buildAnnotatedString {
                            text.split(" ").forEach { word ->
                                if (word == placeholder) {
                                    appendInlineContent(
                                        currentPlaceholder.toString(),
                                        placeholder
                                    )
                                    append(AnnotatedString(text = " "))
                                    currentPlaceholder += 1

                                } else append(AnnotatedString(text = "$word "))
                            }
                        }
                        val inlineContent = listGap.associate { gap ->
                            gap.id.toString() to InlineTextContent(
                                Placeholder(
                                    width = (gap.rightValue.length * 11).sp,
                                    height = 24.sp,
                                    placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
                                )
                            ) {

                                val resultIsCorrect = when (showResult) {
                                    true -> gap.currentValue.value == gap.rightValue
                                    false -> null
                                }

                                val animationDelay = remember(resultIsCorrect) {
                                    if (resultIsCorrect != null) gap.id * AnimateDuration.Fast else 0
                                }

                                val bgColorAnim by animateColorAsState(
                                    targetValue = when (resultIsCorrect) {
                                        true -> Correct
                                        false -> Error
                                        else -> FillUnSelected.copy(0.1f)
                                    },
                                    animationSpec = tween(
                                        durationMillis = AnimateDuration.Long,
                                        delayMillis = animationDelay
                                    )
                                )
                                BasicTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(MaterialTheme.shapes.small)
                                        .border(
                                            width = 1.dp,
                                            color = White.copy(0.1f),
                                            shape = MaterialTheme.shapes.small
                                        )
                                        .background(bgColorAnim),
                                    value = gap.currentValue.value,
                                    onValueChange = { newValue ->
                                        if (newValue.length <= gap.rightValue.length) {
                                            gap.currentValue.value = newValue
                                        }
                                    },
                                    textStyle = TextStyle(
                                        fontFamily = FontFamily.Default,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = White,
                                        textAlign = TextAlign.Center
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onDone = { focusManager.clearFocus() }
                                    ),
                                    enabled = !showResult,
                                    cursorBrush = SolidColor(White),
                                    singleLine = true,
                                )
                            }
                        }
                        BasicText(
                            text = textV,
                            inlineContent = inlineContent,
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.container_small)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.container_small)),
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(id = R.dimen.container_small))
                        .align(Alignment.CenterHorizontally),
                    text = stringResource(id = R.string.filling_in_the_gap_hint),
                    style = MaterialTheme.typography.caption
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(50.dp)
                ) {
                    val currentQuestion = questions[page]
                    AnimatedContent(targetState = currentQuestion.showResult) { shouldShowCheckBtn ->
                        when (shouldShowCheckBtn) {
                            true -> TransitionButtons(
                                onClickNext = viewModel::onClickNext,
                                onClickPrev = viewModel::onClickBack,
                            )
                            false -> CheckButton(
                                enable = true,
                                onClick = viewModel::checkResult,
                            )
                        }
                    }
                }
            }
        }
    }
}