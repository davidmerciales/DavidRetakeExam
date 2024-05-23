package com.example.johndavidmerciales_android_exam.domain.repository

import com.example.davidretakeexam.domain.model.request.GetPersonUseCaseRequest
import com.example.davidretakeexam.domain.model.response.Results
import io.reactivex.rxjava3.core.Observable

interface PersonRepository {

    suspend fun getPersonList(params: GetPersonUseCaseRequest): Observable<Results>
}