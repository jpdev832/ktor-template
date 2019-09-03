package com.staticvillage.ktor.auth.service

import arrow.core.Either
import arrow.core.Try
import com.staticvillage.ktor.auth.manager.AuthManager
import com.staticvillage.ktor.auth.model.Token
import com.staticvillage.ktor.auth.repository.UserRepository

class AuthService(
    private val authManager: AuthManager,
    private val userRepository: UserRepository
) {
    companion object {
        const val SIGNUP_ERROR = "Error creating an account"
        const val LOGIN_ERROR = "Error logging into account"
    }

    fun signup(
        email: String,
        username: String,
        password: String,
        firstName: String,
        lastName: String
    ): Either<String, Token> {
        return Try {
            val passwordHash = authManager.passwordHash(password)
            val userEntity = userRepository.createUser(email, username, passwordHash, firstName, lastName)
            authManager.makeToken(userEntity!!.id)
        }.toEither { SIGNUP_ERROR }
    }

    fun login(username: String, password: String): Either<String, Token> {
        val passwordHash = authManager.passwordHash(password)
        val userEntity = userRepository.getUserByUsername(username)
        return if (passwordHash == userEntity?.passwordHash) {
            Either.right(authManager.makeToken(userEntity.id))
        } else {
            Either.left(LOGIN_ERROR)
        }
    }
}

