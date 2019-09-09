package com.staticvillage.ktor.repositories.postgres.repository

import com.staticvillage.ktor.auth.model.User
import com.staticvillage.ktor.auth.repository.UserRepository
import com.staticvillage.ktor.error.models.ServerError
import com.staticvillage.ktor.error.models.ServerErrorItem
import com.staticvillage.ktor.repositories.postgres.dao.UserEntity
import com.staticvillage.ktor.repositories.postgres.dao.Users
import com.staticvillage.ktor.repositories.postgres.mapper.UserMapper
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.util.*

class UserRepositoryPostgres : UserRepository {
    private val mapper: UserMapper = UserMapper()

    override fun getUser(userId: UUID): User {
        return transaction {
            UserEntity.findById(userId)?.let { mapper.from(it) }
        } ?: throw ServerError(listOf(ServerErrorItem("Error retrieving user information")))
    }

    override fun getUserByUsername(username: String): User {
        return transaction {
            UserEntity.find { Users.username eq username }
                .singleOrNull()
                ?.let { mapper.from(it) }
        } ?: throw ServerError(listOf(ServerErrorItem("Could not retrieve user for given username")))
    }

    override fun createUser(
        email: String,
        username: String,
        password: String,
        firstName: String,
        lastName: String
    ): User {
        return transaction {
            UserEntity.new {
                this.email = email
                this.userName = username
                this.password = password
                this.firstName = firstName
                this.lastName = lastName
                this.createdAt = DateTime.now()
                this.updatedAt = DateTime.now()
            }.let { mapper.from(it) }
        }
    }

    override fun updateUser(userId: UUID, email: String, firstName: String, lastName: String): User {
        return transaction {
            UserEntity.findById(userId)
                ?.apply {
                    this.email = email
                    this.firstName = firstName
                    this.lastName = lastName
                    this.updatedAt = DateTime.now()
                }?.let { mapper.from(it) }
        } ?: throw ServerError(listOf(ServerErrorItem("Error updating user")))
    }

    override fun deleteUser(userId: UUID) {
        transaction { UserEntity.findById(userId) }?.delete()
    }
}