package com.example.supportservice.main.presentation.detailScreen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.supportservice.core.presentation.components.CustomButton
import com.example.supportservice.core.presentation.components.CustomTextField
import com.example.supportservice.main.presentation.components.CustomDropDownMenu
import com.example.supportservice.main.presentation.components.CustomProgressIndicator
import kotlinx.coroutines.delay

@Composable
fun DetailScreen(
    state: DetailState,
    onEvent: (DetailEvent) -> Unit
) {
    LaunchedEffect(state.allStatusesResponseState.response) {
        delay(300)
        if (state.id != 0) {
            Log.e("TAG", "ID->>>${state.id}")
            onEvent(DetailEvent.GetApplicationById(state.id))
        }
    }
    LaunchedEffect(state.updateResponseState.response) {
        if ((state.updateResponseState.response?.statusCode ?: 0) == 200) {
            onEvent(DetailEvent.GoToMain)
        }
    }
    LaunchedEffect(state.selectedRoleId) {
        Log.e("TAG", "ROLE_ID->>${state.selectedRoleId}")
    }

    CustomProgressIndicator(isLoading = state.applicationsResponseState.isLoading)
    state.applicationsResponseState.response?.let { res ->
        res.applications[0].let { response ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Text("Заголовок: " + response.title)
                        Spacer(modifier = Modifier.padding(10.dp))
                        Text("Номер телефона: " + response.phone_number)
                        Spacer(modifier = Modifier.padding(10.dp))
                        Text("Статус: " + response.status)
                        Spacer(modifier = Modifier.padding(10.dp))
                        Text("Описание: " + response.description)
                        if (state.selectedRoleId == 2) {
                            Spacer(modifier = Modifier.padding(10.dp))
                            CustomTextField(
                                value = state.comment,
                                hint = "Комментарии",
                                onValueChange = {
                                    onEvent(DetailEvent.OnCommentChange(it))
                                })
                            Spacer(modifier = Modifier.padding(10.dp))
                            state.allStatusesResponseState.response?.let { statuses ->
                                CustomDropDownMenu(
                                    modifier = Modifier,
                                    suggestions = statuses.allStatuses,
                                    selectedText = state.selectedStatus
                                ) {
                                    onEvent(DetailEvent.OnSelectedStatusChange(it))
                                }
                            }
                            Spacer(modifier = Modifier.padding(10.dp))
                            CustomButton(
                                text = "Сохранить",
                                textSize = 18,
                                isLoading = state.updateResponseState.isLoading
                            ) {
                                onEvent(DetailEvent.UpdateApplication)
                            }
                        }
                    }
                }
            }
        }
    }
}