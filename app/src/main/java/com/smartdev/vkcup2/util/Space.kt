package com.smartdev.vkcup2.util

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun ColumnScope.verticalSpace(height: Dp) = Spacer(modifier = Modifier.height(height))

@Composable
fun RowScope.horizontalSpacer(width: Dp) = Spacer(modifier = Modifier.width(width))

