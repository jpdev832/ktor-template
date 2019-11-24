package com.jparrish.ktor.controller

import io.ktor.application.Application
import io.ktor.application.ApplicationFeature
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.util.AttributeKey

class ControllerRegistrar(configuration: Configuration) {
    val controllers: List<Pair<String, Controller>> = configuration.controllers

    class Configuration {
        var controllers: MutableList<Pair<String, Controller>> = mutableListOf()

        fun register(path: String, controller: Controller) {
            controllers.add(Pair(path, controller))
        }
    }

    companion object Feature : ApplicationFeature<Application, Configuration, ControllerRegistrar> {
        override val key = AttributeKey<ControllerRegistrar>("ControllerRegistrar")

        override fun install(pipeline: Application, configure: Configuration.() -> Unit): ControllerRegistrar {
            val configuration = Configuration().apply(configure)
            val registrar = ControllerRegistrar(configuration)

            registrar.controllers.forEach {
                pipeline.routing {
                    route(it.first) {
                        it.second.register(this)
                    }
                }
            }

            return registrar
        }
    }
}