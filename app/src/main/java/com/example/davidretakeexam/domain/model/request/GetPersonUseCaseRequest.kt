package com.example.davidretakeexam.domain.model.request

data class GetPersonUseCaseRequest(
    var page: String,
    val results: String,
    val seed: String,)
