package com.example.davidretakeexam.presenter.screen.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.davidretakeexam.data.model.PersonEntity
import com.example.davidretakeexam.domain.repository.PersonEntityRepository
import com.example.davidretakeexam.presenter.screen.homescreen.HomeScreenContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val personEntityRepository: PersonEntityRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel()  {

    val state: DetailContract.DetailState = DetailContract.MutableDetailState()
    private val _event = MutableSharedFlow<DetailContract.DetailEvent>()
    private val event: SharedFlow<DetailContract.DetailEvent> = _event.asSharedFlow()


    private val _resultLiveData = MutableLiveData<PersonEntity>()
    val resultLiveData: LiveData<PersonEntity> = _resultLiveData

    init {
//        val personId = savedStateHandle.get<String>("person")
//        if (state.id != "") {
//            viewModelScope.launch {
//                personEntityRepository.getPersonById(state.id).let { person ->
//                    state.title = person.title
//                    state.firstName = person.firstName
//                    state.lastName = person.lastName
//                    state.gender = person.gender
//                    state.birthday = person.birthday
//                    state.age = person.age.toString()
//                    state.emailAddress = person.emailAddress
//                    state.mobileNumber = person.mobileNumber
//                    state.address = person.address
//                    state.profileImg = person.profileImg
//
//                    _resultLiveData.postValue(person)
//                }
//            }
//        }
        viewModelScope.launch {

            event.collect { event ->
//                when (event) {
//                    DetailScreenContract.DetailEvent.OnPopBackClick -> {
//                        appController.sendUiEvent(NavEvent.PopBackStack)
//                    }
//                }
            }
        }
    }
    fun fetchDataById(id: String) {
        viewModelScope.launch {
            try {
                viewModelScope.launch {
                    personEntityRepository.getPersonById(id).let { person ->
                        state.title = person.title
                        state.firstName = person.firstName
                        state.lastName = person.lastName
                        state.gender = person.gender
                        state.birthday = person.birthday
                        state.age = person.age.toString()
                        state.emailAddress = person.emailAddress
                        state.mobileNumber = person.mobileNumber
                        state.address = person.address
                        state.profileImg = person.profileImg

                        _resultLiveData.postValue(person)
                    }
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun setEvent(event: DetailContract.DetailEvent) {
        val newEvent = event
        viewModelScope.launch { _event.emit(newEvent) }
    }
}