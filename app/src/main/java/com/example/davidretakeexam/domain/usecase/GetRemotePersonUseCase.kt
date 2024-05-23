package com.example.davidretakeexam.domain.usecase

import com.example.davidretakeexam.domain.model.request.GetPersonUseCaseRequest
import com.example.davidretakeexam.domain.model.response.Results
import com.example.davidretakeexam.domain.utils.Either
import com.example.davidretakeexam.domain.utils.Failure
import com.example.davidretakeexam.domain.utils.Success
import com.example.johndavidmerciales_android_exam.domain.repository.PersonRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class GetRemotePersonUseCase @Inject constructor(
    private val personRepository: PersonRepository,
) : UseCase<Observable<Results>, GetPersonUseCaseRequest>() {
    override suspend fun run(params: GetPersonUseCaseRequest): Either<Exception, Observable<Results>> {
        return try {
            val observable = personRepository.getPersonList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
            Success(observable)
        } catch (e: Exception) {
            Failure(e)
        }
    }
}