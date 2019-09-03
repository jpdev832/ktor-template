package com.staticvillage.ktor.auth.model

import org.joda.time.DateTime
import java.util.*

data class User(
    val id: UUID,
    val email: String,
    val username: String,
    val passwordHash: String,
    val firstName: String,
    val lastName: String,
    val createdAt: DateTime,
    val updatedAt: DateTime
)