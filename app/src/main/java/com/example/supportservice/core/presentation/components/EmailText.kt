package com.example.supportservice.core.presentation.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.supportservice.R

@Composable
fun EmailText(email: String) {
    val context = LocalContext.current
    Text(
        text = email,
        modifier = Modifier.clickable {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$email")
                putExtra(Intent.EXTRA_SUBJECT, "Тема письма")
            }
            context.startActivity(emailIntent)
        },
        fontSize = 16.sp,
        style = TextStyle(
            fontFamily = FontFamily(
                Font(R.font.montserratlight),
                Font(R.font.montserratlight, FontWeight(100)),
            )
        )
    )
}
