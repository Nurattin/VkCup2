package com.smartdev.vkcup2.ui.screens.rating

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartdev.vkcup2.ui.theme.FillUnSelected
import com.smartdev.vkcup2.ui.theme.MainBackgroundColor
import com.smartdev.vkcup2.util.verticalSpace
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


val ratingList = listOf(14, 8, 4, 1, 1)


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RatingScreen(
    onBackClick: () -> Unit
) {

    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Expanded)
    val coroutineScope = rememberCoroutineScope()


    ModalBottomSheetLayout(
        scrimColor = MainBackgroundColor.copy(0.5f),
        sheetContent = {
            Column(Modifier.fillMaxWidth()) {
                var rating by remember { mutableStateOf(0) }
                var listRating by remember { mutableStateOf(ratingList) }

                Icon(
                    modifier = Modifier
                        .clickable {
                            coroutineScope.launch {
                                sheetState.animateTo(ModalBottomSheetValue.Hidden)
                            }
                        }
                        .padding(16.dp)
                        .align(End),
                    imageVector = Icons.Rounded.Close,
                    contentDescription = null,
                    tint = Color.White
                )
                verticalSpace(height = 16.dp)

                Text(
                    modifier = Modifier.align(CenterHorizontally),
                    text = "Rating the article",
                    style = MaterialTheme.typography.h6,
                    color = Color.White
                )
                verticalSpace(height = 16.dp)
                Box(
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .graphicsLayer(shape = CircleShape, clip = true)
                        .background(FillUnSelected.copy(0.3f))
                        .padding(16.dp)
                ) {


                    Row(
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = CenterVertically
                    ) {
                        Row {
                            repeat(5) { index ->
                                val transition = remember(rating) { Animatable(initialValue = 1f) }
                                val interactionSource = remember { MutableInteractionSource() }
                                val bgColorTransition =
                                    remember(rating) { Animatable(initialValue = 0f) }

                                LaunchedEffect(key1 = rating) {
                                    delay(timeMillis = 75 * index.toLong())
                                    bgColorTransition.animateTo(
                                        targetValue = 1f,
                                        animationSpec = tween(
                                            durationMillis = 250,
                                            easing = LinearEasing
                                        )
                                    )
                                    transition.animateTo(
                                        targetValue = 1.3f,
                                        animationSpec = tween(
                                            durationMillis = 150,
                                            easing = LinearEasing
                                        )
                                    )
                                    transition.animateTo(
                                        targetValue = 1f,
                                        animationSpec = tween(
                                            durationMillis = 100,
                                            easing = LinearEasing
                                        )
                                    )
                                }

                                Icon(
                                    modifier = Modifier
                                        .scale(if (index < rating) transition.value else 1f)
                                        .size(32.dp)
                                        .clickable(
                                            interactionSource = interactionSource,
                                            indication = rememberRipple(),
                                            onClick = {
                                                rating = index + 1
                                                listRating = listRating
                                                    .toMutableList()
                                                    .let {
                                                        val index = listRating.size - 1 - index
                                                        it[index] = ratingList[index] + 1
                                                        it.toList()
                                                    }
                                            }
                                        ),
                                    imageVector = Icons.Rounded.Star,
                                    contentDescription = null,
                                    tint = when (index < rating) {
                                        true -> Color(0xffffd740).copy(bgColorTransition.value)
                                        false -> FillUnSelected.copy(0.3f)
                                    }
                                )
                            }
                        }

                        Text(
                            text = "$rating из 5",
                            style = MaterialTheme.typography.subtitle2,
                            fontWeight = FontWeight.Medium,
                            color = Color.White.copy(0.8f)
                        )

                    }
                }
                verticalSpace(height = 16.dp)

                Text(
                    modifier = Modifier.align(CenterHorizontally),
                    text = "Оценило ${listRating.sum()} человек",
                    style = MaterialTheme.typography.subtitle2,
                    color = Color.White.copy(0.8f)
                )
                verticalSpace(height = 48.dp)

                AnimatedVisibility(visible = rating != 0) {

                    var text by remember { mutableStateOf("") }
                    val interactionSource = remember { MutableInteractionSource() }

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 32.dp)
                            .clip(MaterialTheme.shapes.large)
                            .animateContentSize()
                            .fillMaxWidth()
                            .background(FillUnSelected.copy(0.3f))
                            .padding(16.dp)
                    ) {
                        BasicTextField(
                            value = text,
                            onValueChange = { text = it },
                            modifier = Modifier.fillMaxWidth(1f),
                            textStyle = TextStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                color = Color.White
                            ),
                            cursorBrush = SolidColor(Color.White),
                            interactionSource = interactionSource,
                            enabled = true,
                            visualTransformation = VisualTransformation.None,
                            singleLine = false,
                            maxLines = 5
                        ) { innerTextField ->

                            TextFieldDefaults.TextFieldDecorationBox(
                                value = text,
                                innerTextField = innerTextField,
                                singleLine = false,
                                enabled = true,
                                placeholder = {
                                    Text(
                                        text = "Оставьте сообщение, если хотите",
                                        style = TextStyle(
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 14.sp,
                                        )
                                    )
                                },
                                interactionSource = interactionSource,
                                contentPadding = PaddingValues(0.dp),
                                visualTransformation = VisualTransformation.None
                            )
                        }
                    }
                }
                verticalSpace(height = 16.dp)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 32.dp, end = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listRating.forEachIndexed { index, count ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = CenterVertically
                        ) {

                            Text(
                                text = "${5 - index}",
                                style = MaterialTheme.typography.subtitle2,
                                color = Color.White,
                                fontWeight = FontWeight.Medium
                            )

                            val percentageSelected = remember(count) {
                                ((count / listRating.sumOf { it }.toFloat()) * 100).roundToInt()
                            }

                            val transition = remember(percentageSelected) {
                                Animatable(initialValue = 0f)
                            }

                            LaunchedEffect(percentageSelected) {
                                transition.animateTo(
                                    percentageSelected / 100f,
                                    animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .height(15.dp)
                                    .weight(1f)
                                    .graphicsLayer(
                                        shape = CircleShape,
                                        clip = true
                                    )
                                    .background(FillUnSelected.copy(0.3f)),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                LinearProgressIndicator(
                                    progress = transition.value,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .matchParentSize(),
                                    backgroundColor = Color.Transparent,
                                    color = Color(0xffffd740)
                                )
                            }

                            Text(
                                text = "$percentageSelected %",
                                style = MaterialTheme.typography.subtitle2,
                                color = Color.White,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                verticalSpace(height = 32.dp)
            }
        },
        sheetShape = RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp),
        sheetBackgroundColor = Color(0xff1B1B1B),
        sheetElevation = 8.dp,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MainBackgroundColor)
                .statusBarsPadding()
        ) {
            Icon(
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        onBackClick()
                    },
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = null
            )
            verticalSpace(height = 16.dp)
            Text(text = "Расскрыть", color = Color.White, modifier = Modifier.clickable {
                coroutineScope.launch {
                    sheetState.animateTo(ModalBottomSheetValue.Expanded)
                }
            })
        }
    }
}