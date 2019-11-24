package com.jparrish.ktor.controller.koin

import com.jparrish.ktor.controller.Controller
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

inline fun <reified T : Any> Controller.inject(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): Lazy<T> = lazy { GlobalContext.get().koin.get<T>(qualifier, parameters) }