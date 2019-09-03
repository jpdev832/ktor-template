package com.staticvillage.ktor.repositories.postgres.dao

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.UUIDTable
import java.util.*

object Users : UUIDTable() {
    val email = varchar("email", 255)
    val username = varchar("username", 255).uniqueIndex()
    val password = varchar("password", 255)
    val firstName = text("firstName")
    val lastName = text("lastName")
    val createdAt = datetime("createdAt")
    val updatedAt = datetime("updatedAt")
}

class UserEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UserEntity>(Users)

    var email by Users.email
    var userName by Users.username
    var password by Users.password
    var firstName by Users.firstName
    var lastName by Users.lastName
    var createdAt by Users.createdAt
    var updatedAt by Users.updatedAt
}