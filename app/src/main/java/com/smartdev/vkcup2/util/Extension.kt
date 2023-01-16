package com.smartdev.vkcup2.util

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp


fun <T> List<T>.getNextElementOrFirst(index: Int): Int {
    val targetQuestion = index + 1
    return if (targetQuestion >= size) 0 else targetQuestion
}

fun <T> List<T>.getPrevElementOrLast(index: Int): Int {
    val targetQuestion = index - 1
    return if (targetQuestion <= -1) this.size - 1 else targetQuestion
}

@Composable
fun ColumnScope.verticalSpace(height: Dp) = Spacer(modifier = Modifier.height(height))

@Composable
fun RowScope.horizontalSpacer(width: Dp) = Spacer(modifier = Modifier.width(width))

