package com.smartdev.vkcup2.ui.screens.rating.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.smartdev.vkcup2.ui.theme.FillUnSelected
import com.smartdev.vkcup2.ui.theme.Rating
import kotlin.math.roundToInt

@Composable
fun RatingTable(
    modifier: Modifier = Modifier,
    ratingTable: List<Int>
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ratingTable.forEachIndexed { index, count ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${5 - index}",
                    style = MaterialTheme.typography.subtitle2,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
                val percentageSelected = remember(count) {
                    ((count / ratingTable.sum().toFloat()) * 100)
                }
                Box(
                    modifier = androidx.compose.ui.Modifier
                        .height(15.dp)
                        .weight(1f)
                        .graphicsLayer(
                            shape = CircleShape,
                            clip = true
                        )
                        .background(FillUnSelected.copy(0.3f)),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Spacer(
                        modifier = Modifier
                            .clip(CircleShape)
                            .fillMaxHeight()
                            .fillMaxWidth(percentageSelected / 100f)
                            .background(Rating),
                    )
                }
                Text(
                    modifier = Modifier.fillMaxWidth(0.1f),
                    textAlign = TextAlign.End,
                    text = "${percentageSelected.roundToInt()} %",
                    style = MaterialTheme.typography.subtitle2,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}