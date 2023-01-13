package com.smartdev.vkcup2.ui.screens.rating.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartdev.vkcup2.R
import com.smartdev.vkcup2.ui.theme.FillSelected
import com.smartdev.vkcup2.ui.theme.FillUnSelected
import com.smartdev.vkcup2.ui.theme.Rating
import com.smartdev.vkcup2.util.horizontalSpacer

@Composable
fun RatingFloatingActionButton(
    modifier: Modifier = Modifier,
    userRating: Int?,
    numberAppraisers: String,
    onClickRating: () -> Unit
) {
    Row(
        modifier = modifier
            .clip(CircleShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = onClickRating
            )
            .background(FillUnSelected)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Star,
                contentDescription = null,
                tint = when (userRating != null) {
                    true -> Rating
                    false -> Color.White.copy(0.5f)
                }
            )
            Text(
                text = numberAppraisers,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White.copy(0.5f)
            )
            horizontalSpacer(width = 4.dp)
            Divider(
                modifier = Modifier
                    .height(24.dp)
                    .width(1.dp),
                color = FillSelected
            )
            horizontalSpacer(width = 4.dp)
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_message),
                contentDescription = null,
                tint = Color.White.copy(0.5f)
            )
        }
    }
}