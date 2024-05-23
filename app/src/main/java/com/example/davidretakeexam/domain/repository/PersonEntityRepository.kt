package com.example.davidretakeexam.domain.repository
import com.example.davidretakeexam.data.model.PersonEntity
import kotlinx.coroutines.flow.Flow

interface PersonEntityRepository {

    suspend fun getPersonById(id: String?): PersonEntity
    suspend fun insertPersons(person: List<PersonEntity>)

    suspend fun getPersons(): Flow<List<PersonEntity>>

    suspend fun deleteAllPersons()
}