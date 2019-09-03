package com.staticvillage.ktor.injection

import com.staticvillage.ktor.auth.service.AuthService
import org.koin.dsl.module

val serviceModule = module {
    factory { AuthService(get(), get()) }
}