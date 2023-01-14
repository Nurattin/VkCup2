package com.smartdev.vkcup2.ui.screens.rating.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import com.smartdev.vkcup2.R
import com.smartdev.vkcup2.ui.theme.FillUnSelected
import com.smartdev.vkcup2.ui.theme.Rating

@Composable
fun RatingFloatingActionButton(
    modifier: Modifier = Modifier,
    numberAppraisers: String,
    onClickRating: () -> Unit,
    isFavorite: Boolean
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = onClickRating
            )
            .background(FillUnSelected)
            .padding(dimensionResource(id = R.dimen.container_small)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = numberAppraisers,
            style = MaterialTheme.typography.h4,
            color = if (isFavorite) Rating else Color.White.copy(0.5f)
        )
    }
}