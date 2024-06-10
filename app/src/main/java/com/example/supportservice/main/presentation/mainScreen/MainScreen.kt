package com.example.supportservice.main.presentation.mainScreen

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.supportservice.main.domain.main.models.application.Application
import com.example.supportservice.main.domain.main.models.status.Status
import com.example.supportservice.main.presentation.components.ApplicationItem
import com.example.supportservice.main.presentation.components.CustomProgressIndicator
import com.example.supportservice.main.presentation.components.StatusItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    state: MainState,
    scaffoldState: ScaffoldState,
    onEvent: (MainEvent) -> Unit
) {

    LaunchedEffect(state.isAdded) {
        if (state.isAdded) {
            Log.e("ISADDED", "IS_ADDED->${state.selectedRoleId}")
            if (state.selectedRoleId == 1) {
                onEvent(MainEvent.GetApplicationsByEmail)
            }
            if (state.selectedRoleId == 2) {
                onEvent(MainEvent.GetAllApplications)
            }
        }
    }
    LaunchedEffect(true) {
        onEvent(MainEvent.GetAllStatuses)
    }
    CustomProgressIndicator(isLoading = state.applicationsRespState.isLoading)
    LaunchedEffect(state.selectedStatus) {
        val sortedApplications = mutableListOf<Application>()
        state.applicationsRespState.response?.let { res ->
            if (state.selectedStatus != "Все")
                res.applications.forEach {
                    if (state.selectedStatus == it.status) {
                        sortedApplications.add(it)
                    }
                } else {
                sortedApplications.addAll(res.applications)
            }
            onEvent(MainEvent.OnSortedApplicationsChange(sortedApplications))
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
        backgroundColor = Color.Transparent,
        floatingActionButton = {
            if (state.selectedRoleId == 1)
                FloatingActionButton(onClick = {
                    onEvent(MainEvent.GoToApplication)
                    onEvent(MainEvent.OnIsAddedChange(false))
                }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "AddApplication")
                }
        }
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

                    state.sortedApplications.forEach { app ->
                        ApplicationItem(application = app) {
                            scope.launch {
                                onEvent(MainEvent.OnCommentChange(it.comment))
                                delay(150)
                                Log.e("TAG", "IDIDID->$it")
                                onEvent(MainEvent.UpdateStatus(it.id))
                                delay(300)
                                onEvent(MainEvent.GoToDetailApplication)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.padding(50.dp))
            }
        }
    }
}


