package com.staticvillage.ktor.repositories.postgres.dao

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.UUIDTable
import java.util.*

object Examples : UUIDTable(name = "Examples") {
    val data = varchar("data", 255)
    val createdAt = datetime("createdAt")
    val updatedAt = datetime("updatedAt")
}

class ExampleEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<ExampleEntity>(Examples)

    var data by Examples.data
    var createdAt by Examples.createdAt
    var updatedAt by Examples.updatedAt
}