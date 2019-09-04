package com.staticvillage.ktor.repositories.postgres

import com.staticvillage.ktor.repositories.Database
import com.staticvillage.ktor.repositories.postgres.dao.Examples
import com.staticvillage.ktor.repositories.postgres.dao.Users
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.Database as ExposedDatabase

class DatabasePostgres(private val url: String, private val user: String, private val password: String) : Database {
    override fun connect() {
        ExposedDatabase.connect(makeDataSource(url, user, password))
        transaction {
            SchemaUtils.create(Examples, Users)
        }
    }

    private fun makeDataSource(url: String, user: String, password: String): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "org.postgresql.Driver"
        config.jdbcUrl = url
        config.username = user
        config.password = password
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }
}