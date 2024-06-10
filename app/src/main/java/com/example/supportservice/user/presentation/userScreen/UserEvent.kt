package com.example.supportservice.user.presentation.userScreen

sealed class UserEvent {

    data class OnNameChange(val name: String) : UserEvent()
    data class OnPhoneNumberChange(val phoneNumberChange: String) : UserEvent()
    object UpdateUser : UserEvent()
    object GetUser : UserEvent()
}