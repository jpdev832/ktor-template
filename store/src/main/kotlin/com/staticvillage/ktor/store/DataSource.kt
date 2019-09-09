package com.staticvillage.ktor.store

interface DataSource {
    fun connect()
    fun disconnect()
}