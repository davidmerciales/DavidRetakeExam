package com.example.davidretakeexam.domain.usecase

import com.example.davidretakeexam.domain.repository.PersonEntityRepository
import javax.inject.Inject

class DeleteAllPersonsUseCase @Inject constructor(
    private val personEntityRepository: PersonEntityRepository
) {

    suspend operator fun invoke(){
        personEntityRepository.deleteAllPersons()
    }
}