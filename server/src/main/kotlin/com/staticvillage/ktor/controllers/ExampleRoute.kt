package com.staticvillage.ktor.controllers

import com.staticvillage.ktor.models.requests.ExampleRequest
import com.staticvillage.ktor.services.ExampleService
import com.staticvillage.ktor.util.toUUID
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.auth.authentication
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.exampleRoutes() {
    val service: ExampleService by inject()

    authenticate {
        post("/examples") {
            val example = call.receive<ExampleRequest>()
            call.respond(HttpStatusCode.OK, service.createExample(example.data))
        }

        get("/examples") {
            call.respond(HttpStatusCode.OK, service.getAllExamples())
        }

        get("/examples/{id}") {
            val principal = call.authentication.principal<JWTPrincipal>()!!
//            val id = it.payload.id
//            val userId = id.toUUID()
            val exampleId = call.parameters["id"]!!.toUUID()
            call.respond(HttpStatusCode.OK, service.getExample(exampleId))
        }

        put("/examples/{id}") {
            val principal = call.authentication.principal<JWTPrincipal>()!!
            val exampleId = call.parameters["id"]!!.toUUID()
            val example = call.receive<ExampleRequest>()
            call.respond(HttpStatusCode.OK, service.updateExample(exampleId, example.data))
        }

        delete("/examples/{id}") {
            val principal = call.authentication.principal<JWTPrincipal>()!!
            val exampleId = call.parameters["id"]!!.toUUID()
            call.respond(HttpStatusCode.OK, service.deleteExample(exampleId))
        }
    }
}
