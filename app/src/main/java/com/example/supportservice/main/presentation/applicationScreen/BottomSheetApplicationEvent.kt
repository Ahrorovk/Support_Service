package com.example.supportservice.main.presentation.applicationScreen

sealed class BottomSheetApplicationEvent {
    data class OnDescriptionChange(val state: String) : BottomSheetApplicationEvent()
    data class OnTitleChange(val state: String) : BottomSheetApplicationEvent()
    data class OnEmailChange(val state: String) : BottomSheetApplicationEvent()
    data class OnPhoneNumberChange(val number: String) : BottomSheetApplicationEvent()
    object Apply : BottomSheetApplicationEvent()
    object GoToMain : BottomSheetApplicationEvent()
}
