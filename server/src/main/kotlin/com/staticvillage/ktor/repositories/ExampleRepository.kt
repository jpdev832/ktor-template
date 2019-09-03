package com.staticvillage.ktor.repositories

import com.staticvillage.ktor.models.Example
import java.util.*

interface ExampleRepository {
    fun getExample(id: UUID): Example?
    fun getAllExamples(): List<Example>
    fun createExample(data: String): Example?
    fun updateExample(id: UUID, data: String): Example?
    fun deleteExample(id: UUID): Boolean
}