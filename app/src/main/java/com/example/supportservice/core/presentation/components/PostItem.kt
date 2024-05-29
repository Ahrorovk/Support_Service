package com.example.supportservice.core.presentation.components

import com.example.supportservice.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PostItem(
    title: String,
    picture: Int,
    tag: String = "",
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .clip(RoundedCornerShape(24.dp))
            .border(1.dp, Color(0xFFBDBDBD), RoundedCornerShape(24.dp))
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f),
            painter = painterResource(id = picture),
            contentDescription = "postItem"
        )

        Spacer(modifier = Modifier.padding(6.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
        ) {

            if (tag.isNotEmpty()) {
                Text(
                    text = title, fontSize = 20.sp, style = TextStyle(
                        fontFamily = FontFamily(
                            Font(R.font.montserratregular),
                            Font(R.font.montserratregular, FontWeight.W200)
                        )
                    )
                )
            }


            Spacer(modifier = Modifier.padding(6.dp))

            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (tag.isNotEmpty()) {
                    TagItem(tag = tag)
                } else {
                    Text(
                        text = title, fontSize = 20.sp, style = TextStyle(
                            fontFamily = FontFamily(
                                Font(R.font.montserratregular),
                                Font(R.font.montserratregular, FontWeight.W200)
                            )
                        )
                    )
                }
                PostItemButton {
                    onClick()
                }
            }

            Spacer(modifier = Modifier.padding(12.dp))
        }
    }
}