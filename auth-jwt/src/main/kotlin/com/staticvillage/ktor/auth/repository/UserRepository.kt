package com.staticvillage.ktor.auth.repository

import com.staticvillage.ktor.auth.model.User
import java.util.*

interface UserRepository {
    fun getUser(userId: UUID): User
    fun getUserByUsername(username: String): User
    fun createUser(
        email: String,
        username: String,
        password: String,
        firstName: String,
        lastName: String
    ): User

    fun updateUser(
        userId: UUID,
        email: String,
        firstName: String,
        lastName: String
    ): User

    fun deleteUser(userId: UUID)
}