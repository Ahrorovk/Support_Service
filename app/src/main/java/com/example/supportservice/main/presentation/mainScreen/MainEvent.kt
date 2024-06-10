package com.example.supportservice.main.presentation.mainScreen

import com.example.supportservice.main.domain.main.models.application.Application
import com.example.supportservice.main.domain.main.states.application.GetApplicationsResponseState

sealed class MainEvent {
    data class OnGetApplicationsResponseStateChange(val state: GetApplicationsResponseState) :
        MainEvent()


    data class OnCommentChange(val state: String) : MainEvent()

    data class UpdateStatus(val id: Int) : MainEvent()
    data class OnSortedApplicationsChange(val sortedApplicationsChange: List<Application>) :
        MainEvent()

    data class OnSelectedStatusChange(val status: String) : MainEvent()

    data class OnSearchProjectChange(val state: String) : MainEvent()
    data class OnIsAddedChange(val state: Boolean) : MainEvent()
    object GoToApplication : MainEvent()
    object GetApplicationsByEmail : MainEvent()
    object GoToDetailApplication : MainEvent()
    object GetUser : MainEvent()
    object GetAllStatuses : MainEvent()
    object GetAllApplications : MainEvent()
    object GoToMyApplication : MainEvent()
    object GoToAuthorization : MainEvent()
    object GoToRegistration : MainEvent()
    object GoToSettings : MainEvent()
    object Logout : MainEvent()
    object Clean : MainEvent()
}
