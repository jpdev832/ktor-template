package com.staticvillage.ktor.services

import com.staticvillage.ktor.models.responses.ExampleListResponse
import com.staticvillage.ktor.models.responses.ExampleResponse
import com.staticvillage.ktor.repositories.ExampleRepository
import java.util.*

class ExampleService(private val exampleRepo: ExampleRepository) {
    fun getAllExamples(): ExampleListResponse {
        val result = exampleRepo.getAllExamples()
        return ExampleListResponse(result)
    }

    fun getExample(exampleId: UUID): ExampleResponse {
        val result = exampleRepo.getExample(exampleId)
        return ExampleResponse(result!!)
    }

    fun createExample(data: String): ExampleResponse {
        val result = exampleRepo.createExample(data)
        return ExampleResponse(result!!)
    }

    fun updateExample(exampleId: UUID, data: String): ExampleResponse {
        val result = exampleRepo.updateExample(exampleId, data)
        return ExampleResponse(result!!)
    }

    fun deleteExample(exampleId: UUID) {
        exampleRepo.deleteExample(exampleId)
    }
}