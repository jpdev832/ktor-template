package com.staticvillage.ktor.error.models

data class ServerErrorResponse(
    val errors: List<ServerErrorItem>
)