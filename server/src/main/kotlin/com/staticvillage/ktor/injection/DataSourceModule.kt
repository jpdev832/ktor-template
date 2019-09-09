package com.staticvillage.ktor.injection

import com.staticvillage.ktor.auth.repository.UserRepository
import com.staticvillage.ktor.repositories.ExampleRepository
import com.staticvillage.ktor.repositories.postgres.repository.ExampleRepositoryPostgres
import com.staticvillage.ktor.repositories.postgres.repository.UserRepositoryPostgres
import org.koin.dsl.module

val dataSourceModule = module {
    factory<UserRepository> { UserRepositoryPostgres() }
    factory<ExampleRepository> { ExampleRepositoryPostgres() }
}