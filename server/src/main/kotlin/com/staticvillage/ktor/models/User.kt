package com.staticvillage.ktor.models

data class User(
    val email: String,
    val password: String,
    val username: String,
    val firstName: String,
    val lastName: String
)