package com.example.supportservice.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.supportservice.R

@Composable
fun TagItem(
    tag: String
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.secondary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .padding(5.dp),
            text = tag,
            fontSize = 18.sp,
            style = TextStyle(
                fontFamily = FontFamily(
                    Font(R.font.axiformamedium),
                    Font(R.font.axiformamedium, FontWeight.W200)
                )
            ),
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}