package com.example.davidretakeexam.domain.usecase

import com.example.davidretakeexam.data.model.PersonEntity
import com.example.davidretakeexam.domain.repository.PersonEntityRepository
import javax.inject.Inject

class InsertPersonUseCase @Inject constructor(
    private val personEntityRepository: PersonEntityRepository
){
    suspend operator fun invoke(persons: List<PersonEntity>) {
        personEntityRepository.insertPersons(persons)
    }
}