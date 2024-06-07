package com.example.supportservice.main.presentation.mainScreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.supportservice.core.presentation.components.ProgressIndicator
import com.example.supportservice.main.domain.main.models.application.Application
import com.example.supportservice.main.domain.main.models.status.Status
import com.example.supportservice.main.presentation.components.ApplicationItem
import com.example.supportservice.main.presentation.components.StatusItem
import kotlinx.coroutines.delay

@Composable
fun MainScreen(
    state: MainState,
    scaffoldState: ScaffoldState,
    onEvent: (MainEvent) -> Unit
) {
    val sorted = remember {
        mutableListOf<Application>()
    }
    LaunchedEffect(true) {
        onEvent(MainEvent.GetAllStatuses)
        onEvent(MainEvent.OnSelectedStatusChange("Все"))
    }
    LaunchedEffect(state.selectedStatus) {
        delay(500)
        sorted.clear()
        state.applicationsRespState.response?.let { res ->
            if (state.selectedStatus != "Все")
                res.applications.forEach {
                    if (state.selectedStatus == it.status) {
                        sorted.add(it)
                    }
                } else {
                sorted.addAll(res.applications)
            }
        }
    }
    val scope = rememberCoroutineScope()
    LaunchedEffect(state.selectedRoleId) {
        if (state.selectedRoleId == 0) {
            onEvent(MainEvent.GetUser)
        }
        if (state.selectedRoleId == 1) {
            onEvent(MainEvent.GetApplicationsByEmail)
        }
        if (state.selectedRoleId == 2) {
            onEvent(MainEvent.GetAllApplications)
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = Color.Transparent
    ) { itt ->
        LazyColumn(Modifier.padding(itt)) {
            item {
                state.applicationsRespState.response?.let { res ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        if (state.allStatusesResponseState.isLoading) {
                            LinearProgressIndicator()
                        }
                        state.allStatusesResponseState.response?.let { statusResp ->
                            val newStatuses = ArrayList<Status>()
                            newStatuses.add(Status(0, "Все"))
                            statusResp.allStatuses.forEachIndexed { index, status ->
                                newStatuses.add(Status(status.id + 1, status.status))
                            }
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp)
                            ) {
                                items(newStatuses) { status ->
                                    StatusItem(
                                        title = status.status,
                                        isSelected = state.selectedStatus == status.status
                                    ) {
                                        onEvent(MainEvent.OnSelectedStatusChange(it))
                                    }
                                    Spacer(modifier = Modifier.padding(10.dp))
                                }
                            }
                        }
                    }

                    sorted.forEach { app ->
                        ApplicationItem(application = app){

                        }
                    }
                }

                Spacer(modifier = Modifier.padding(50.dp))
            }
        }
    }
    ProgressIndicator(isProgress = false/*state.vacanciesRespState.isLoading*/)
}


