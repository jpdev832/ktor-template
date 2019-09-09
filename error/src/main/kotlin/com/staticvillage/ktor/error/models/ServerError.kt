package com.staticvillage.ktor.error.models

import io.ktor.http.HttpStatusCode

data class ServerError(
    val errors: List<ServerErrorItem>,
    val status: HttpStatusCode = HttpStatusCode.BadRequest
) : Exception()

data class ServerErrorItem(
    val message: String,
    val code: Int = 1000,
    val description: String? = null
)