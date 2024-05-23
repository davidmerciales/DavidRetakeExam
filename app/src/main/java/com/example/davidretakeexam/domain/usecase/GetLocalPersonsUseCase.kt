package com.example.davidretakeexam.domain.usecase

import com.example.davidretakeexam.data.model.PersonEntity
import com.example.davidretakeexam.domain.repository.PersonEntityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocalPersonsUseCase @Inject constructor(
    private val personEntityRepository: PersonEntityRepository
) {
    suspend operator fun invoke(): Flow<List<PersonEntity>>{
        return personEntityRepository.getPersons()
    }
}
//fun List<PersonEntity>.mapToPerson(): Flow<List<Person>> {
//    return map {
//        with(it) {
//            Person(
//                title = title,
//                firstName = firstName,
//                lastName = lastName,
//                gender = gender,
//                birthday = birthday,
//                age = age,
//                emailAddress = emailAddress,
//                mobileNumber = mobileNumber,
//                address = address
//            )
//        }
//    }
//}