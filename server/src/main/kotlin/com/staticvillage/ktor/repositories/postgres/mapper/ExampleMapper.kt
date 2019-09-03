package com.staticvillage.ktor.repositories.postgres.mapper

import com.staticvillage.ktor.models.Example
import com.staticvillage.ktor.repositories.postgres.dao.ExampleEntity
import com.staticvillage.ktor.repositories.postgres.dao.Examples
import org.jetbrains.exposed.sql.ResultRow

class ExampleMapper {
    fun from(resultRow: ResultRow): Example {
        return Example(
            id = resultRow[Examples.id].value,
            data = resultRow[Examples.data]
        )
    }

    fun from(exampleEntity: ExampleEntity): Example {
        return Example(
            id = exampleEntity.id.value,
            data = exampleEntity.data
        )
    }
}