package com.staticvillage.ktor.controllers

import com.jparrish.ktor.controller.Controller
import com.jparrish.ktor.controller.koin.inject
import com.staticvillage.ktor.auth.service.AuthService
import com.staticvillage.ktor.models.Login
import com.staticvillage.ktor.models.User
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post

class AuthController : Controller {
    private val authService: AuthService by inject()

    override fun register(route: Route) {
        route.post("/signup") {
            val user = call.receive<User>()
            val result = authService.signup(user.email, user.username, user.password, user.firstName, user.lastName)
            call.respond(HttpStatusCode.OK, result)
        }

        route.post("/login") {
            val credential = call.receive<Login>()
            val result = authService.login(credential.username, credential.password)
            call.respond(HttpStatusCode.OK, result)
        }
    }
}