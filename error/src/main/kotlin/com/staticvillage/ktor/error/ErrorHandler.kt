package com.staticvillage.ktor.error

import com.staticvillage.ktor.error.models.ServerError
import com.staticvillage.ktor.error.models.ServerErrorItem
import com.staticvillage.ktor.error.models.ServerErrorResponse
import io.ktor.application.call
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond

class ErrorHandler {
    companion object {
        private val defaultErrorItem: ServerErrorItem = (ServerErrorItem("An error has occurred"))
        val defaultError: ServerError = ServerError(listOf(defaultErrorItem), HttpStatusCode.BadRequest)
    }

    fun configure(configuration: StatusPages.Configuration) {
        configuration.status(HttpStatusCode.NotFound) {
            call.respond(defaultError.status, ServerErrorResponse(defaultError.errors))
        }
        configuration.exception<Throwable> { cause ->
            val error = (cause as? ServerError)?.let { it } ?: defaultError
            call.respond(error.status, ServerErrorResponse(error.errors))
            throw cause
        }
    }
}