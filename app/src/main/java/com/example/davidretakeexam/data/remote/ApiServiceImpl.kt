package com.example.davidretakeexam.data.remote

import com.example.davidretakeexam.domain.model.request.GetPersonUseCaseRequest
import com.example.davidretakeexam.domain.model.response.Results
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import javax.inject.Inject
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.rx3.asCoroutineDispatcher
import kotlinx.coroutines.rx3.rxSingle
import kotlinx.serialization.json.Json

class ApiServiceImpl @Inject constructor(
    private val httpClient: HttpClient
) : ApiService {

    override suspend fun getPersonList(params: GetPersonUseCaseRequest): Observable<Results> {
        return rxSingle(Schedulers.io().asCoroutineDispatcher()) {
            val response: HttpResponse = httpClient.get("https://randomuser.me/api/") {
                parameter("nat", "US")
                parameter("page", params.page)
                parameter("results", params.results)
                parameter("seed", params.seed)
            }
            val responseBody = response.bodyAsText()
            Json.decodeFromString<Results>(responseBody)
        }.toObservable()
    }
}