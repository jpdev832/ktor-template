package com.staticvillage.ktor.repositories.postgres.mapper

import com.staticvillage.ktor.repositories.postgres.dao.UserEntity
import com.staticvillage.ktor.auth.model.User

class UserMapper {
    fun from(userEntity: UserEntity): User {
        return User(
            id = userEntity.id.value,
            email = userEntity.email,
            username = userEntity.userName,
            passwordHash = userEntity.password,
            firstName = userEntity.firstName,
            lastName = userEntity.lastName,
            createdAt = userEntity.createdAt,
            updatedAt = userEntity.updatedAt
        )
    }
}