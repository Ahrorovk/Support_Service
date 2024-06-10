package com.example.supportservice.main.presentation.applicationScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.supportservice.core.presentation.components.CustomButton
import com.example.supportservice.core.presentation.components.CustomTextField
import com.example.supportservice.core.presentation.components.ModalSheetDefaultStick

@Composable
fun BottomSheetApplication(
    state: BottomSheetApplicationState,
    onEvent: (BottomSheetApplicationEvent) -> Unit
) {
    LaunchedEffect(state.applicationResponseState.response) {
        if ((state.applicationResponseState.response?.statusCode ?: 0) == 200) {
            onEvent(BottomSheetApplicationEvent.GoToMain)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .fillMaxHeight(0.6f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ModalSheetDefaultStick(modifier = Modifier.padding(top = 16.dp))
        Spacer(modifier = Modifier.padding(15.dp))
        Text(
            modifier = Modifier.padding(vertical = 10.dp),
            text = "Новая заявка",
            color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.padding(15.dp))

        CustomTextField(
            value = state.title,
            hint = "Заголовок",
            onValueChange = { onEvent(BottomSheetApplicationEvent.OnTitleChange(it)) }
        )

        Spacer(modifier = Modifier.padding(15.dp))

        CustomTextField(
            value = state.description,
            hint = "Описание",
            onValueChange = { onEvent(BottomSheetApplicationEvent.OnDescriptionChange(it)) }
        )
        Spacer(modifier = Modifier.padding(15.dp))
        CustomButton(
            text = "Создать",
            textSize = 14,
            isLoading = state.applicationResponseState.isLoading,
            modifier = Modifier.width(140.dp)
        ) {
            onEvent(BottomSheetApplicationEvent.Apply)
        }
    }
}