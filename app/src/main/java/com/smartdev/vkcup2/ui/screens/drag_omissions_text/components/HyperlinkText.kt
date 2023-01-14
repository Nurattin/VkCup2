package com.smartdev.vkcup2.ui.screens.drag_omissions_text.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.smartdev.vkcup2.ui.theme.FillUnSelected

@Composable
fun HyperlinkText(
    modifier: Modifier = Modifier,
    fullText: String,
    hyperLinks: List<String>,
    textStyle: TextStyle = TextStyle.Default,
    linkTextColor: Color = Color.White,
    onClickLick: (String) -> Unit
) {
    val annotatedString = buildAnnotatedString {
        append(fullText)
        for (value in hyperLinks) {
            val startIndex = fullText.indexOf(value)
            val endIndex = startIndex + value.length
            addStyle(
                style = SpanStyle(
                    color = linkTextColor,
                    textDecoration = TextDecoration.combine(
                        listOf(TextDecoration.Underline)
                    ),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                ),
                start = startIndex,
                end = endIndex
            )
            addStringAnnotation(
                tag = "URL",
                annotation = value,
                start = startIndex,
                end = endIndex
            )
        }
        addStyle(
            style = SpanStyle(
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
            ),
            start = 0,
            end = fullText.length
        )
    }

    ClickableText(
        modifier = modifier,
        text = annotatedString,
        style = textStyle,
        onClick = {
            annotatedString
                .getStringAnnotations("URL", it, it)
                .firstOrNull()?.let { stringAnnotation ->
                    onClickLick(stringAnnotation.item)
                }
        }
    )
}

@Composable
fun HyperlinkText2(
    modifier: Modifier = Modifier,
    fullText: String,
    hyperLinks: List<Int>,
    textStyle: TextStyle = TextStyle.Default,
    linkTextColor: Color = Color.White,
    onClickLick: (String) -> Unit
) {
    val annotatedString = buildAnnotatedString {
        append(fullText)
        for (value in hyperLinks) {
            val endIndex = value + 5
            addStyle(
                style = SpanStyle(
                    color = linkTextColor,
                    background = FillUnSelected,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                ),
                start = value,
                end = endIndex
            )
            addStringAnnotation(
                tag = "URL",
                annotation = fullText.substring(value..endIndex),
                start = value,
                end = endIndex
            )
        }
        addStyle(
            style = SpanStyle(
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
            ),
            start = 0,
            end = fullText.length
        )
    }

    ClickableText(
        modifier = modifier,
        text = annotatedString,
        style = textStyle,
        onClick = {
            annotatedString
                .getStringAnnotations("URL", it, it)
                .firstOrNull()?.let { stringAnnotation ->
                    onClickLick(stringAnnotation.item)
                }
        }
    )
}