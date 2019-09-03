package com.staticvillage.ktor.repositories.postgres.repository

import com.staticvillage.ktor.models.Example
import com.staticvillage.ktor.repositories.ExampleRepository
import com.staticvillage.ktor.repositories.postgres.dao.ExampleEntity
import com.staticvillage.ktor.repositories.postgres.dao.Examples
import com.staticvillage.ktor.repositories.postgres.mapper.ExampleMapper
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.util.*

class ExampleRepositoryPostgres : ExampleRepository {
    private val mapper: ExampleMapper = ExampleMapper()

    override fun getExample(id: UUID): Example? {
        return transaction { ExampleEntity.findById(id)?.let { mapper.from(it) } }
    }

    override fun getAllExamples(): List<Example> {
        return transaction {
            Examples.selectAll()
                .map {
                    mapper.from(it)
                }
        }
    }

    override fun createExample(data: String): Example? {
        return transaction {
            ExampleEntity.new {
                this.data = data
                this.createdAt = DateTime.now()
                this.updatedAt = DateTime.now()
            }.let { mapper.from(it) }
        }
    }

    override fun updateExample(id: UUID, data: String): Example? {
        return transaction {
            ExampleEntity.findById(id)?.apply {
                this.data = data
                updatedAt = DateTime.now()
            }?.let { mapper.from(it) }
        }
    }

    override fun deleteExample(id: UUID): Boolean {
        return transaction {
            ExampleEntity.findById(id)?.delete()?.let { true } ?: false
        }
    }
}