package com.staticvillage.ktor.auth.service

import com.staticvillage.ktor.auth.manager.AuthManager
import com.staticvillage.ktor.auth.model.Token
import com.staticvillage.ktor.auth.repository.UserRepository
import com.staticvillage.ktor.error.models.ServerError
import com.staticvillage.ktor.error.models.ServerErrorItem

class AuthService(
    private val authManager: AuthManager,
    private val userRepository: UserRepository
) {
    companion object {
        const val LOGIN_ERROR = "Error logging into account"
    }

    fun signup(
        email: String,
        username: String,
        password: String,
        firstName: String,
        lastName: String
    ): Token {
        val passwordHash = authManager.passwordHash(password)
        val userEntity = userRepository.createUser(email, username, passwordHash, firstName, lastName)
        return authManager.makeToken(userEntity.id)
    }

    fun login(username: String, password: String): Token {
        val passwordHash = authManager.passwordHash(password)
        val userEntity = userRepository.getUserByUsername(username)

        if (passwordHash == userEntity.passwordHash) {
            return authManager.makeToken(userEntity.id)
        } else {
            throw ServerError(listOf(ServerErrorItem(LOGIN_ERROR)))
        }
    }
}

