package com.staticvillage.ktor.util

import com.staticvillage.ktor.models.Error
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond

object ResponseUtil {
    suspend fun handleSuccess(call: ApplicationCall, message: Any) {
        call.respond(HttpStatusCode.OK, message)
    }

    suspend fun handleError(call: ApplicationCall, error: Error) {
        call.respond(HttpStatusCode.BadRequest, error)
    }
}