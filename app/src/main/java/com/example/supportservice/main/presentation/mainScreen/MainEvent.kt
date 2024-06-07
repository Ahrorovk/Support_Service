package com.example.supportservice.main.presentation.mainScreen

import com.example.supportservice.main.domain.main.states.application.GetApplicationsResponseState

sealed class MainEvent {
    //    data class OnGetVacanciesRespStateChange(val state: GetVacancyRespState) : MainEvent()
    data class OnGetApplicationsResponseStateChange(val state: GetApplicationsResponseState) :
        MainEvent()

    data class OnSelectedStatusChange(val status: String) : MainEvent()

    data class OnSearchProjectChange(val state: String) : MainEvent()
    data class GoToApplication(val id: Int) : MainEvent()
    object GetApplicationsByEmail : MainEvent()
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
