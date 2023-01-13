package com.smartdev.vkcup2.ui.screens.rating.components

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.smartdev.vkcup2.R
import com.smartdev.vkcup2.ui.theme.FillSelected
import com.smartdev.vkcup2.ui.theme.FillUnSelected
import com.smartdev.vkcup2.ui.theme.Rating

@Composable
fun ColumnScope.RatingBar(
    rating: Int,
    onClickRating: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .clip(CircleShape)
            .background(FillUnSelected.copy(0.3f))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                repeat(5) { index ->
                    val transition = remember(rating) { Animatable(1f) }
                    val interactionSource = remember { MutableInteractionSource() }
                    val bgColorTransition = remember(rating) { Animatable(FillUnSelected) }

                    LaunchedEffect(key1 = rating) {
                        bgColorTransition.animateTo(
                            targetValue = Rating,
                            animationSpec = tween(
                                durationMillis = 250,
                                delayMillis = 50 * index,
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
                            .size(dimensionResource(id = R.dimen.star))
                            .clickable(
                                interactionSource = interactionSource,
                                indication = rememberRipple(
                                    radius = 16.dp,
                                    color = FillSelected,
                                ),
                                onClick = { onClickRating(index + 1) }
                            ),
                        imageVector = Icons.Rounded.Star,
                        contentDescription = null,
                        tint = when (index < rating) {
                            true -> bgColorTransition.value
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
}