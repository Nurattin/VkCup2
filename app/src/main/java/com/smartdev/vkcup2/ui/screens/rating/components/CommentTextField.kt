package com.smartdev.vkcup2.ui.screens.rating.components

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.smartdev.vkcup2.R
import com.smartdev.vkcup2.ui.theme.FillUnSelected
import com.smartdev.vkcup2.ui.theme.SentButton
import com.smartdev.vkcup2.util.AnimateEasing.EaseInBack
import kotlinx.coroutines.delay

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun CommentTextField(
    modifier: Modifier = Modifier,
    onSentComment: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val density = LocalDensity.current
    var text by remember { mutableStateOf("") }
    var textFieldSize by remember { mutableStateOf(0.dp) }
    var sentIconIsVisibility by remember { mutableStateOf(true) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.container_medium))
            .onSizeChanged {
                with(density) { textFieldSize = it.height.toDp() }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.large)
                .weight(1f)
                .background(FillUnSelected.copy(0.3f))
                .padding(16.dp)
        ) {
            BasicTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.subtitle2,
                cursorBrush = SolidColor(Color.White),
                enabled = true,
                visualTransformation = VisualTransformation.None,
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                maxLines = 5
            ) { innerTextField ->

                TextFieldDefaults.TextFieldDecorationBox(
                    value = text,
                    innerTextField = innerTextField,
                    singleLine = true,
                    enabled = true,
                    placeholder = {
                        Text(
                            text = "Оставьте сообщение, если хотите",
                            style = MaterialTheme.typography.subtitle2,
                            color = Color.White.copy(0.5f),
                            maxLines = 1
                        )
                    },
                    interactionSource = remember { MutableInteractionSource() },
                    contentPadding = PaddingValues(0.dp),
                    visualTransformation = VisualTransformation.None
                )
            }
        }
        Box(
            modifier = modifier
                .size(textFieldSize)
                .clip(MaterialTheme.shapes.large)
                .background(SentButton)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                    onClick = {
                        if (text.isNotEmpty()) {
                            focusManager.clearFocus()
                            onSentComment(text)
                            sentIconIsVisibility = false
                            text = ""
                        }
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            LaunchedEffect(key1 = sentIconIsVisibility) {
                if (!sentIconIsVisibility) {
                    delay(1000)
                    sentIconIsVisibility = true
                }
            }

            androidx.compose.animation.AnimatedVisibility(
                visible = sentIconIsVisibility,
                exit = slideOutHorizontally(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = EaseInBack,
                    ),
                    targetOffsetX = { it }
                ),
                enter = EnterTransition.None
            ) {
                Icon(
                    imageVector = Icons.Rounded.Send,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}