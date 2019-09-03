package com.staticvillage.ktor.injection

import com.staticvillage.ktor.auth.manager.AuthManager
import com.staticvillage.ktor.auth.manager.JwtAuthManager
import org.koin.dsl.module

const val PROPERTY_AUTH_JWT_AUDIENCE = "property_auth_jwt_audience"
const val PROPERTY_AUTH_JWT_REALM = "property_auth_jwt_realm"
const val PROPERTY_AUTH_JWT_SECRET = "property_auth_jwt_secret"
const val PROPERTY_AUTH_JWT_ISSUER = "property_auth_jwt_issuer"
const val PROPERTY_AUTH_JWT_SALT = "property_auth_jwt_salt"

val authModule = module {
    single<AuthManager> {
        JwtAuthManager(
            getProperty(PROPERTY_AUTH_JWT_AUDIENCE),
            getProperty(PROPERTY_AUTH_JWT_REALM),
            getProperty(PROPERTY_AUTH_JWT_SECRET),
            getProperty(PROPERTY_AUTH_JWT_ISSUER),
            getProperty(PROPERTY_AUTH_JWT_SALT)
        )
    }
}