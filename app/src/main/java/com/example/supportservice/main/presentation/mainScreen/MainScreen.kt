package com.example.supportservice.main.presentation.mainScreen

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.filled.Backup
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.supportservice.core.presentation.components.CustomDialog
import com.example.supportservice.main.domain.main.models.application.Application
import com.example.supportservice.main.domain.main.models.status.Status
import com.example.supportservice.main.presentation.components.ApplicationItem
import com.example.supportservice.main.presentation.components.CustomProgressIndicator
import com.example.supportservice.main.presentation.components.StatusItem
import com.example.supportservice.main.presentation.createAndSavePdf
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    state: MainState,
    scaffoldState: ScaffoldState,
    onEvent: (MainEvent) -> Unit
) {

    val context = LocalContext.current
    var permissionGranted by remember { mutableStateOf(false) }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        permissionGranted = isGranted
        if (!isGranted) {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) -> {
                permissionGranted = true
            }

            else -> {
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }


    CustomDialog(
        text = "Вы хотите удалить заявку?",
        description = "Заявка " +
                (if (state.applicationsRespState.response?.applications?.isNotEmpty() == true)
                    state.applicationsRespState.response.applications.sortedBy { it.id == state.deleteByIdApplicationState }[0].title
                else "") +
                "будет удалена",
        okBtnClick =
        {
            onEvent(MainEvent.DeleteApplicationById(state.deleteByIdApplicationState))
            onEvent(MainEvent.OnDeleteApplicationDialogState(false))
        },
        cancelBtnClick = {
            onEvent(MainEvent.OnDeleteApplicationDialogState(false))
        },
        isDialogOpen = state.deleteApplicationDialogState,
        okBtnText = "Удалить",
        cancelBtnText = "Отменить"
    ) {
        onEvent(MainEvent.OnDeleteApplicationDialogState(false))
    }
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
        if (state.selectedRoleId == 1) {
            onEvent(MainEvent.GetApplicationsByEmail)
        }
        if (state.selectedRoleId == 2) {
            onEvent(MainEvent.GetAllApplications)
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
                } else {
                FloatingActionButton(onClick = {
                    if (permissionGranted) {
                        state.applicationsRespState.response?.let {
                            createAndSavePdf(
                                context,
                                it.applications
                            )
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Storage permission is required",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }) {
                    Icon(imageVector = Icons.Default.Backup, contentDescription = "AddApplication")
                }
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
                        ApplicationItem(application = app,
                            onDeleteApplication = {
                                onEvent(MainEvent.OnDeleteApplicationByIdStateChange(it))
                                onEvent(MainEvent.OnDeleteApplicationDialogState(true))
                            }
                        ) {
                            scope.launch {
                                onEvent(MainEvent.OnCommentChange(it.comment ?: ""))
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

fun main() {

}