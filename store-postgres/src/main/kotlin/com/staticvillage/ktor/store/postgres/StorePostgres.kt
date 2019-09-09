package com.staticvillage.ktor.store.postgres

import com.staticvillage.ktor.store.DataSource
import com.staticvillage.ktor.store.Store
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction

class DatabasePostgres(
    private val tables: Array<Table>,
    private val url: String,
    private val user: String,
    private val password: String,
    private val driverClassName: String = "org.postgresql.Driver",
    private val maximumPoolSize: Int = 3,
    private val isAutoCommit: Boolean = false,
    private val transactionIsolation: String = "TRANSACTION_REPEATABLE_READ"
) : DataSource {
    override fun connect() {
        Database.connect(makeDataSource())
        transaction {
            SchemaUtils.create(*tables)
        }
    }

    override fun disconnect() {
    }

    private fun makeDataSource(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = driverClassName
        config.jdbcUrl = url
        config.username = user
        config.password = password
        config.maximumPoolSize = maximumPoolSize
        config.isAutoCommit = isAutoCommit
        config.transactionIsolation = transactionIsolation
        config.validate()
        return HikariDataSource(config)
    }
}

fun Store.Configuration.postgres(
    tables: Array<Table>,
    url: String,
    user: String,
    password: String,
    driverClassName: String = "org.postgresql.Driver",
    maximumPoolSize: Int = 3,
    isAutoCommit: Boolean = false,
    transactionIsolation: String = "TRANSACTION_REPEATABLE_READ"
) {
    val dataSource: DataSource = DatabasePostgres(
        tables,
        url,
        user,
        password,
        driverClassName,
        maximumPoolSize,
        isAutoCommit,
        transactionIsolation
    )
    register(dataSource)
}