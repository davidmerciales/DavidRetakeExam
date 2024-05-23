package com.example.davidretakeexam.presenter.screen.homescreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.davidretakeexam.data.model.PersonEntity

class HomeScreenContract {

    sealed interface HomeEvent {
        data object OnLoadMore : HomeEvent
        data object OnSwipeRefresh : HomeEvent
        data class OnPersonItemClicked(val id: String): HomeEvent

    }

    interface HomeState {
        var test: String
        var page: Int
        var seed: String
        var results: Int
        var isLoading: Boolean
        var isRefreshing: Boolean
    }

    class MutableHomeState : HomeState {
        override var test: String by mutableStateOf("")
        override var page: Int by mutableIntStateOf(1)
        override var seed: String by mutableStateOf("abc")
        override var results: Int by mutableIntStateOf(15)
        override var isLoading: Boolean by mutableStateOf(false)
        override var isRefreshing: Boolean by mutableStateOf(false)
    }
}
