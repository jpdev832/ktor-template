package com.staticvillage.ktor.controllers

import com.staticvillage.ktor.models.Login
import com.staticvillage.ktor.models.User
import com.staticvillage.ktor.models.Error
import com.staticvillage.ktor.util.ResponseUtil
import com.staticvillage.ktor.auth.service.AuthService
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.routing.Route
import io.ktor.routing.post
import org.koin.ktor.ext.inject

fun Route.authRoutes() {
    val authService: AuthService by inject()

    post("/signup") {
        val user = call.receive<User>()
        val result = authService.signup(user.email, user.username, user.password, user.firstName, user.lastName)
        result.fold({ ResponseUtil.handleError(call, Error(it)) }) { ResponseUtil.handleSuccess(call, it) }
    }

    post("/login") {
        val credential = call.receive<Login>()
        val result = authService.login(credential.username, credential.password)
        result.fold({ ResponseUtil.handleError(call, Error(it)) }) { ResponseUtil.handleSuccess(call, it) }
    }
}