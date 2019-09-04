package com.staticvillage.ktor

import com.fasterxml.jackson.databind.SerializationFeature
import com.staticvillage.ktor.auth.manager.AuthManager
import com.staticvillage.ktor.controllers.authRoutes
import com.staticvillage.ktor.controllers.exampleRoutes
import com.staticvillage.ktor.injection.*
import com.staticvillage.ktor.repositories.Database
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.jwt
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.routing.route
import io.ktor.routing.routing
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
                PROPERTY_DB_URL to environment.config.property("ktor.db.url").getString(),
                PROPERTY_DB_USERNAME to environment.config.property("ktor.db.username").getString(),
                PROPERTY_DB_PASSWORD to environment.config.property("ktor.db.password").getString(),
                PROPERTY_AUTH_JWT_AUDIENCE to environment.config.property("ktor.jwt.audience").getString(),
                PROPERTY_AUTH_JWT_REALM to environment.config.property("ktor.jwt.realm").getString(),
                PROPERTY_AUTH_JWT_SECRET to environment.config.property("ktor.jwt.secret").getString(),
                PROPERTY_AUTH_JWT_ISSUER to environment.config.property("ktor.jwt.issuer").getString(),
                PROPERTY_AUTH_JWT_SALT to environment.config.property("ktor.jwt.salt").getString()
            )
        )
    }

    val authManager: AuthManager by inject()

    // Move to feature
    val database: Database by inject()
    database.connect()

    install(Authentication) {
        jwt { authManager.configure(this) }
    }

    install(ContentNegotiation) {
        jackson {
            configure(SerializationFeature.INDENT_OUTPUT, true)
        }
    }

    routing {
        route("/api/v1") {
            authRoutes()
            exampleRoutes()
        }
    }
}

