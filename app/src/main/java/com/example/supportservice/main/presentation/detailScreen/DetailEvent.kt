package com.example.supportservice.main.presentation.detailScreen

sealed class DetailEvent {
    data class OnIdChange(val id: Int) : DetailEvent()
    data class OnCommentChange(val comment: String) : DetailEvent()
    data class OnSelectedStatusChange(val status: String) : DetailEvent()

    object GetAllStatuses : DetailEvent()
    data class GetApplicationById(val id: Int) : DetailEvent()
    object UpdateApplication : DetailEvent()
    object GoToMain : DetailEvent()
}