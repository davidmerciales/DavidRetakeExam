package com.example.johndavidmerciales_android_exam.data.repository

import android.annotation.SuppressLint
import com.example.davidretakeexam.data.remote.ApiService
import com.example.davidretakeexam.domain.model.request.GetPersonUseCaseRequest
import com.example.davidretakeexam.domain.model.response.Results
import com.example.johndavidmerciales_android_exam.domain.repository.PersonRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class PersonRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): PersonRepository {
    override suspend fun getPersonList(params: GetPersonUseCaseRequest): Observable<Results> {
        return apiService.getPersonList(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}