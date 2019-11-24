package com.jparrish.ktor.controller

import io.ktor.routing.Route

interface Controller {
    fun register(route: Route)
}