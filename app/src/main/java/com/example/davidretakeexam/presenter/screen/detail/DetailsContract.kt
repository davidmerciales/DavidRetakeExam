package com.example.davidretakeexam.presenter.screen.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class DetailContract {

    sealed interface DetailEvent {
        data object OnPopBackClick: DetailEvent
    }

    interface DetailState {
        var id: String?
        var title: String
        var firstName: String
        var lastName: String
        var gender: String
        var birthday: String
        var age: String
        var emailAddress: String
        var mobileNumber: String
        var address: String
        var profileImg: String
    }

    class MutableDetailState: DetailState {
        override var id: String? by mutableStateOf("")
        override var title: String by mutableStateOf("")
        override var firstName: String by mutableStateOf("")
        override var lastName: String by mutableStateOf("")
        override var gender: String by mutableStateOf("")
        override var birthday: String by mutableStateOf("")
        override var age: String by mutableStateOf("")
        override var emailAddress: String by mutableStateOf("")
        override var mobileNumber: String by mutableStateOf("")
        override var address: String by mutableStateOf("")
        override var profileImg: String by mutableStateOf("")
    }
}