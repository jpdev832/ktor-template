package com.staticvillage.ktor.injection

import org.koin.core.module.Module

val appComponent: List<Module> = listOf(
    authModule,
    dataSourceModule,
    serviceModule
)