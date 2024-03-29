package com.staticvillage.ktor

import com.fasterxml.jackson.databind.SerializationFeature
import com.jparrish.ktor.controller.ControllerRegistrar
import com.staticvillage.ktor.auth.manager.AuthManager
import com.staticvillage.ktor.controllers.AuthController
import com.staticvillage.ktor.controllers.ExampleController
import com.staticvillage.ktor.error.ErrorHandler
import com.staticvillage.ktor.injection.*
import com.staticvillage.ktor.repositories.postgres.dao.Examples
import com.staticvillage.ktor.repositories.postgres.dao.Users
import com.staticvillage.ktor.store.Store
import com.staticvillage.ktor.store.postgres.postgres
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.jwt
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.jackson.jackson
import io.ktor.util.KtorExperimentalAPI
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalAPI
fun Application.module() {
    install(Koin) {
        modules(appComponent)
        properties(
            mapOf(
                PROPERTY_AUTH_JWT_AUDIENCE to environment.config.property("ktor.jwt.audience").getString(),
                PROPERTY_AUTH_JWT_REALM to environment.config.property("ktor.jwt.realm").getString(),
                PROPERTY_AUTH_JWT_SECRET to environment.config.property("ktor.jwt.secret").getString(),
                PROPERTY_AUTH_JWT_ISSUER to environment.config.property("ktor.jwt.issuer").getString(),
                PROPERTY_AUTH_JWT_SALT to environment.config.property("ktor.jwt.salt").getString()
            )
        )
    }

    val authManager: AuthManager by inject()

    install(Store) {
        postgres(
            tables = arrayOf(Examples, Users),
            url = environment.config.property("ktor.db.url").getString(),
            user = environment.config.property("ktor.db.username").getString(),
            password = environment.config.property("ktor.db.password").getString()
        )
    }

    install(Authentication) {
        jwt { authManager.configure(this) }
    }

    install(ContentNegotiation) {
        jackson {
            configure(SerializationFeature.INDENT_OUTPUT, true)
        }
    }

    install(StatusPages) {
        ErrorHandler().configure(this)
    }

    install(ControllerRegistrar) {
        register("/api/v1/auth", AuthController())
        register("/api/v1/auth/examples", ExampleController())
    }
}

