package com.example.davidretakeexam.data.di

import com.example.davidretakeexam.data.remote.ApiService
import com.example.davidretakeexam.domain.repository.PersonEntityRepository
import com.example.davidretakeexam.domain.usecase.GetRemotePersonUseCase
import com.example.johndavidmerciales_android_exam.data.local.dao.PersonDao
import com.example.davidretakeexam.data.repository.PersonEntityRepositoryImpl
import com.example.johndavidmerciales_android_exam.data.repository.PersonRepositoryImpl
import com.example.johndavidmerciales_android_exam.domain.repository.PersonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesPersonRepository(apiService: ApiService): PersonRepository {
        return PersonRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun providesPersonEntityRepositoryImpl(dao: PersonDao): PersonEntityRepository {
        return PersonEntityRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun providesGetRemotePersonUseCase(personRepository: PersonRepository): GetRemotePersonUseCase {
        return GetRemotePersonUseCase(personRepository)
    }
}