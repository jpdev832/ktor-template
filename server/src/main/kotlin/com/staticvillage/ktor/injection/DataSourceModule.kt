package com.staticvillage.ktor.injection

import com.staticvillage.ktor.auth.repository.UserRepository
import com.staticvillage.ktor.repositories.Database
import com.staticvillage.ktor.repositories.ExampleRepository
import com.staticvillage.ktor.repositories.postgres.DatabasePostgres
import com.staticvillage.ktor.repositories.postgres.repository.ExampleRepositoryPostgres
import com.staticvillage.ktor.repositories.postgres.repository.UserRepositoryPostgres
import org.koin.dsl.module

const val PROPERTY_DB_URL = "property_db_url"
const val PROPERTY_DB_USERNAME = "property_db_username"
const val PROPERTY_DB_PASSWORD = "property_db_password"

val dataSourceModule = module {
    single<Database> {
        DatabasePostgres(
            getProperty(PROPERTY_DB_URL),
            getProperty(PROPERTY_DB_USERNAME),
            getProperty(PROPERTY_DB_PASSWORD)
        )
    }
    factory<UserRepository> { UserRepositoryPostgres() }
    factory<ExampleRepository> { ExampleRepositoryPostgres() }
}