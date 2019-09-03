package com.staticvillage.ktor.auth.manager

import com.staticvillage.ktor.auth.model.Token
import io.ktor.auth.jwt.JWTAuthenticationProvider
import java.util.*

interface AuthManager {
    fun configure(provider: JWTAuthenticationProvider)
    fun makeToken(id: UUID): Token
    fun passwordHash(password: String): String
}