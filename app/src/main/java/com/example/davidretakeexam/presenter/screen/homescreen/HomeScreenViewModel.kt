package com.example.davidretakeexam.presenter.screen.homescreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.davidretakeexam.BaseApp
import com.example.davidretakeexam.data.model.PersonEntity
import com.example.davidretakeexam.domain.model.request.GetPersonUseCaseRequest
import com.example.davidretakeexam.domain.model.response.Results
import com.example.davidretakeexam.domain.usecase.DeleteAllPersonsUseCase
import com.example.davidretakeexam.domain.usecase.GetLocalPersonsUseCase
import com.example.davidretakeexam.domain.usecase.GetRemotePersonUseCase
import com.example.davidretakeexam.domain.usecase.InsertPersonUseCase
import com.example.davidretakeexam.presenter.utils.toDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx3.collect
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getPersonRemoteUseCase: GetRemotePersonUseCase,
    private val getLocalPersonsUseCase: GetLocalPersonsUseCase,
    private val insertPersonUseCase: InsertPersonUseCase,
    private val deleteAllPersonsUseCase: DeleteAllPersonsUseCase
) : ViewModel() {

    val state: HomeScreenContract.HomeState = HomeScreenContract.MutableHomeState()
    private val _event = MutableSharedFlow<HomeScreenContract.HomeEvent>()
    private val event: SharedFlow<HomeScreenContract.HomeEvent> = _event.asSharedFlow()


    private val _resultLiveData = MutableLiveData<List<PersonEntity>>()
    val resultLiveData: LiveData<List<PersonEntity>> = _resultLiveData

    init {
        getRemotePersonList(false)
        getInitialLocalPersonList()

        viewModelScope.launch {

            event.collect { event ->
                when (event) {
                    HomeScreenContract.HomeEvent.OnLoadMore -> {
                        getRemotePersonList(true)
                        getInitialLocalPersonList()
                    }

                    HomeScreenContract.HomeEvent.OnSwipeRefresh -> {
                        refreshList()
                    }

                    is HomeScreenContract.HomeEvent.OnPersonItemClicked -> {
                       // appController.sendUiEvent(NavEvent.Navigate(Routes.DetailScreen + "?personId=${event.id}"))
                    }
                }
            }
        }
    }

    private fun refreshList() {
        state.isRefreshing = true
        viewModelScope.launch {
            try {
                deleteAllPersonsUseCase.invoke()
                state.page = 0

                // Optionally add some delay to simulate the refreshing time
                delay(500)

                getRemotePersonList(false)
                getInitialLocalPersonList()
            } catch (e: Exception) {
                // Handle the exception, maybe log or show a message
                state.isRefreshing = false
            }
        }
    }


    @SuppressLint("SuspiciousIndentation")
    private fun getInitialLocalPersonList() = viewModelScope.launch {
        if (state.isRefreshing) state.isRefreshing = false
            getLocalPersonsUseCase().collect { personsRaw ->
                _resultLiveData.postValue(personsRaw)

                if (state.isRefreshing) state.isRefreshing = false
            }
    }

    private fun getRemotePersonList(isLoadMore: Boolean) = viewModelScope.launch {
        if (isLoadMore) {
            state.isLoading = true
        }
        getPersonRemoteUseCase(
            params = GetPersonUseCaseRequest(
                page = if (isLoadMore) (state.page ++).toString() else state.page.toString(),
                results = state.results.toString(),
                seed = state.seed
            ),
            onSuccess = { results ->
                    results.collect { data ->

                        insertPersonUseCase(
                            data.results.map { result ->
                            PersonEntity(
                                id = result.login.uuid,
                                title = result.name.title,
                                firstName = result.name.first,
                                lastName = result.name.last,
                                gender = result.gender,
                                birthday = result.dob.date.toDate(),
                                age = result.dob.age,
                                emailAddress = result.email,
                                mobileNumber = result.cell,
                                address = "${result.location.state}, ${result.location.city}",
                                profileImg = result.picture.large
                            )
                        })
                    }

//                insertPersonUseCase(
//                    results.results.map { person ->
//                        state.page = results.info.page
//
//                        PersonEntity(
//                            id = person.login.uuid,
//                            title = person.name.title,
//                            firstName = person.name.first,
//                            lastName = person.name.last,
//                            gender = person.gender,
//                            birthday = person.dob.date.toDate(),
//                            age = person.dob.age,
//                            emailAddress = person.email,
//                            mobileNumber = person.cell,
//                            address = "${person.location.state}, ${person.location.city}",
//                            profileImg = person.picture.large
//                        )
//                    }
//                )
                state.isLoading = false
                state.isRefreshing = false
            },
            onFailure = {
                state.isLoading = false
                state.isRefreshing = false
            }
        )
    }


    fun setEvent(event: HomeScreenContract.HomeEvent) {
        val newEvent = event
        viewModelScope.launch { _event.emit(newEvent) }
    }
}

