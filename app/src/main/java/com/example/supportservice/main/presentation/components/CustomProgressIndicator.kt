package com.example.supportservice.main.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.supportservice.core.presentation.ui.theme.AppColor

@Composable
fun CustomProgressIndicator(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false
) {
    if (isLoading) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = AppColor, trackColor = Color.Red)
        }
    }
}