package com.smartdev.vkcup2.ui.screens.rating.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartdev.vkcup2.ui.theme.FillUnSelected

@Composable
fun User(
    userName: String,
    extraParams: String
) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            modifier = androidx.compose.ui.Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(FillUnSelected),
            imageVector = Icons.Rounded.AccountCircle,
            contentDescription = null
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = userName,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = extraParams,
                color = Color.White.copy(0.5f),
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

