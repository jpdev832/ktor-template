package com.staticvillage.ktor.database

import com.staticvillage.ktor.repositories.postgres.dao.Examples
import com.staticvillage.ktor.repositories.postgres.dao.Users
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.config.ApplicationConfig
import io.ktor.util.KtorExperimentalAPI
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

@KtorExperimentalAPI
object DatabaseFactory {
    fun init(config: ApplicationConfig) {
        val url = config.property("db.url").getString()
        val user = config.property("db.user").getString()
        val password = config.property("db.password").getString()

        Database.connect(makeDataSource(url, user, password))
        transaction {
            SchemaUtils.createMissingTablesAndColumns(Examples, Users)
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