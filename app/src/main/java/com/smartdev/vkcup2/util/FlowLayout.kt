package com.smartdev.vkcup2.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.*
import kotlin.math.max

@Composable
fun FlowRow(
    modifier: Modifier = Modifier,
    mainAxisSize: SizeMode = SizeMode.Wrap,
    mainAxisAlignment: FlowMainAxisAlignment = MainAxisAlignment.Start,
    mainAxisSpacing: Dp = 0.dp,
    crossAxisAlignment: FlowCrossAxisAlignment = FlowCrossAxisAlignment.Start,
    crossAxisSpacing: Dp = 0.dp,
    lastLineMainAxisAlignment: FlowMainAxisAlignment = mainAxisAlignment,
    content: @Composable () -> Unit
) {
    Flow(
        modifier = modifier,
        orientation = LayoutOrientation.Horizontal,
        mainAxisSize = mainAxisSize,
        mainAxisAlignment = mainAxisAlignment,
        mainAxisSpacing = mainAxisSpacing,
        crossAxisAlignment = crossAxisAlignment,
        crossAxisSpacing = crossAxisSpacing,
        lastLineMainAxisAlignment = lastLineMainAxisAlignment,
        content = content
    )
}

@Composable
fun FlowColumn(
    modifier: Modifier = Modifier,
    mainAxisSize: SizeMode = SizeMode.Wrap,
    mainAxisAlignment: FlowMainAxisAlignment = MainAxisAlignment.Start,
    mainAxisSpacing: Dp = 0.dp,
    crossAxisAlignment: FlowCrossAxisAlignment = FlowCrossAxisAlignment.Start,
    crossAxisSpacing: Dp = 0.dp,
    lastLineMainAxisAlignment: FlowMainAxisAlignment = mainAxisAlignment,
    content: @Composable () -> Unit
) {
    Flow(
        modifier = modifier,
        orientation = LayoutOrientation.Vertical,
        mainAxisSize = mainAxisSize,
        mainAxisAlignment = mainAxisAlignment,
        mainAxisSpacing = mainAxisSpacing,
        crossAxisAlignment = crossAxisAlignment,
        crossAxisSpacing = crossAxisSpacing,
        lastLineMainAxisAlignment = lastLineMainAxisAlignment,
        content = content
    )
}

enum class FlowCrossAxisAlignment {
    Center,

    Start,

    End,
}

typealias FlowMainAxisAlignment = MainAxisAlignment

@Composable
private fun Flow(
    modifier: Modifier,
    orientation: LayoutOrientation,
    mainAxisSize: SizeMode,
    mainAxisAlignment: FlowMainAxisAlignment,
    mainAxisSpacing: Dp,
    crossAxisAlignment: FlowCrossAxisAlignment,
    crossAxisSpacing: Dp,
    lastLineMainAxisAlignment: FlowMainAxisAlignment,
    content: @Composable () -> Unit
) {
    fun Placeable.mainAxisSize() =
        if (orientation == LayoutOrientation.Horizontal) width else height

    fun Placeable.crossAxisSize() =
        if (orientation == LayoutOrientation.Horizontal) height else width

    Layout(content, modifier) { measurables, outerConstraints ->
        val sequences = mutableListOf<List<Placeable>>()
        val crossAxisSizes = mutableListOf<Int>()
        val crossAxisPositions = mutableListOf<Int>()

        var mainAxisSpace = 0
        var crossAxisSpace = 0

        val currentSequence = mutableListOf<Placeable>()
        var currentMainAxisSize = 0
        var currentCrossAxisSize = 0

        val constraints = OrientationIndependentConstraints(outerConstraints, orientation)

        val childConstraints = if (orientation == LayoutOrientation.Horizontal) {
            Constraints(maxWidth = constraints.mainAxisMax)
        } else {
            Constraints(maxHeight = constraints.mainAxisMax)
        }

        fun canAddToCurrentSequence(placeable: Placeable) =
            currentSequence.isEmpty() || currentMainAxisSize + mainAxisSpacing.roundToPx() +
                    placeable.mainAxisSize() <= constraints.mainAxisMax

        fun startNewSequence() {
            if (sequences.isNotEmpty()) {
                crossAxisSpace += crossAxisSpacing.roundToPx()
            }
            sequences += currentSequence.toList()
            crossAxisSizes += currentCrossAxisSize
            crossAxisPositions += crossAxisSpace

            crossAxisSpace += currentCrossAxisSize
            mainAxisSpace = max(mainAxisSpace, currentMainAxisSize)

            currentSequence.clear()
            currentMainAxisSize = 0
            currentCrossAxisSize = 0
        }

        for (measurable in measurables) {
            val placeable = measurable.measure(childConstraints)

            if (!canAddToCurrentSequence(placeable)) startNewSequence()

            if (currentSequence.isNotEmpty()) {
                currentMainAxisSize += mainAxisSpacing.roundToPx()
            }
            currentSequence.add(placeable)
            currentMainAxisSize += placeable.mainAxisSize()
            currentCrossAxisSize = max(currentCrossAxisSize, placeable.crossAxisSize())
        }

        if (currentSequence.isNotEmpty()) startNewSequence()

        val mainAxisLayoutSize = if (constraints.mainAxisMax != Constraints.Infinity &&
            mainAxisSize == SizeMode.Expand
        ) {
            constraints.mainAxisMax
        } else {
            max(mainAxisSpace, constraints.mainAxisMin)
        }
        val crossAxisLayoutSize = max(crossAxisSpace, constraints.crossAxisMin)

        val layoutWidth = if (orientation == LayoutOrientation.Horizontal) {
            mainAxisLayoutSize
        } else {
            crossAxisLayoutSize
        }
        val layoutHeight = if (orientation == LayoutOrientation.Horizontal) {
            crossAxisLayoutSize
        } else {
            mainAxisLayoutSize
        }

        layout(layoutWidth, layoutHeight) {
            sequences.forEachIndexed { i, placeables ->
                val childrenMainAxisSizes = IntArray(placeables.size) { j ->
                    placeables[j].mainAxisSize() +
                            if (j < placeables.lastIndex) mainAxisSpacing.roundToPx() else 0
                }
                val arrangement = if (i < sequences.lastIndex) {
                    mainAxisAlignment.arrangement
                } else {
                    lastLineMainAxisAlignment.arrangement
                }
                val mainAxisPositions = IntArray(childrenMainAxisSizes.size) { 0 }
                with(arrangement) {
                    arrange(mainAxisLayoutSize, childrenMainAxisSizes, mainAxisPositions)
                }
                placeables.forEachIndexed { j, placeable ->
                    val crossAxis = when (crossAxisAlignment) {
                        FlowCrossAxisAlignment.Start -> 0
                        FlowCrossAxisAlignment.End ->
                            crossAxisSizes[i] - placeable.crossAxisSize()
                        FlowCrossAxisAlignment.Center ->
                            Alignment.Center.align(
                                IntSize.Zero,
                                IntSize(
                                    width = 0,
                                    height = crossAxisSizes[i] - placeable.crossAxisSize()
                                ),
                                LayoutDirection.Ltr
                            ).y
                    }
                    if (orientation == LayoutOrientation.Horizontal) {
                        placeable.place(
                            x = mainAxisPositions[j],
                            y = crossAxisPositions[i] + crossAxis
                        )
                    } else {
                        placeable.place(
                            x = crossAxisPositions[i] + crossAxis,
                            y = mainAxisPositions[j]
                        )
                    }
                }
            }
        }
    }
}

enum class SizeMode {
    Wrap,
    Expand
}

enum class MainAxisAlignment(internal val arrangement: Arrangement.Vertical) {
    Center(Arrangement.Center),

    Start(Arrangement.Top),

    End(Arrangement.Bottom),

    SpaceEvenly(Arrangement.SpaceEvenly),

    SpaceBetween(Arrangement.SpaceBetween),

    SpaceAround(Arrangement.SpaceAround);
}