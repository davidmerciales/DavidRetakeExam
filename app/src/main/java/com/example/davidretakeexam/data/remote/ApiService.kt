package com.example.davidretakeexam.data.remote

import com.example.davidretakeexam.domain.model.request.GetPersonUseCaseRequest
import com.example.davidretakeexam.domain.model.response.Results
import io.reactivex.rxjava3.core.Observable

interface ApiService {

    suspend fun getPersonList(params: GetPersonUseCaseRequest): Observable<Results>
}