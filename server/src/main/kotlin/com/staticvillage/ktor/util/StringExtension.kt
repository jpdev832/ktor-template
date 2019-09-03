package com.staticvillage.ktor.util

import java.util.*

fun String.toUUID(): UUID {
    return UUID.fromString(this)
}