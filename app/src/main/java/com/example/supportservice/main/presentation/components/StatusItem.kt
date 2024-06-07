package com.example.supportservice.main.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun StatusItem(
    title: String,
    isSelected: Boolean,
    onClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(if (isSelected) Color.DarkGray else MaterialTheme.colorScheme.onPrimary)
            .clickable {
                onClick(title)
            }, contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 5.dp)
        )
    }
}