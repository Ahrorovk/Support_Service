package com.example.supportservice.user.presentation.userScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.supportservice.core.presentation.components.CustomButton
import com.example.supportservice.core.presentation.components.CustomTextField

@Composable
fun UserScreen(
    state: UserState,
    onEvent: (UserEvent) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(state.userResponseState) {
        state.userResponseState.response?.let {
            onEvent(UserEvent.OnNameChange(it.name))
            onEvent(UserEvent.OnPhoneNumberChange(it.phone_number))
        }
        if (state.userResponseState.error.isNotEmpty()) {
            if (state.userResponseState.error == "User is successful updated")
                onEvent(UserEvent.GetUser)
            Toast.makeText(context, state.userResponseState.error, Toast.LENGTH_SHORT).show()
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                CustomTextField(
                    value = state.name,
                    hint = "Name",
                    onValueChange = {
                        onEvent(UserEvent.OnNameChange(it))
                    }
                )

                Spacer(modifier = Modifier.padding(15.dp))

                CustomTextField(
                    value = state.userResponseState.response?.email ?: "",
                    hint = "Email",
                    onValueChange = {
                    },
                    isAvailable = false
                )

                Spacer(modifier = Modifier.padding(15.dp))

                CustomTextField(
                    value = state.phoneNumber,
                    hint = "Phone number",
                    onValueChange = {
                        onEvent(UserEvent.OnPhoneNumberChange(it))
                    },
                    keyboardType = KeyboardType.Number
                )

                Spacer(modifier = Modifier.padding(25.dp))

                CustomButton(
                    text = "Сохранить",
                    textSize = 18,
                    isLoading = state.userResponseState.isLoading
                ) {
                    onEvent(UserEvent.UpdateUser)
                }
            }
        }
    }
}