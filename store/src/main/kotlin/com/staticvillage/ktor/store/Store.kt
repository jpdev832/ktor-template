package com.staticvillage.ktor.store

import io.ktor.application.Application
import io.ktor.application.ApplicationFeature
import io.ktor.application.ApplicationStopping
import io.ktor.util.AttributeKey

class Store(configuration: Configuration) {
    val dataSources: List<DataSource> = configuration.dataSources

    class Configuration {
        var dataSources: MutableList<DataSource> = mutableListOf()

        fun register(dataSource: DataSource) {
            dataSources.add(dataSource)
        }
    }

    companion object Feature : ApplicationFeature<Application, Configuration, Store> {
        override val key = AttributeKey<Store>("Store")

        override fun install(pipeline: Application, configure: Configuration.() -> Unit): Store {
            val configuration = Configuration().apply(configure)
            val storage = Store(configuration)

            storage.dataSources.forEach { it.connect() }
            pipeline.environment.monitor.subscribe(ApplicationStopping) {
                storage.dataSources.forEach { it.disconnect() }
            }

            return storage
        }
    }
}