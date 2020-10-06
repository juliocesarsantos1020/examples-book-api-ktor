package com.jcsantos.br.com.jcsantos.poc.controller

import com.jcsantos.br.com.jcsantos.poc.domain.Book
import com.jcsantos.br.com.jcsantos.poc.extension.objectToJson
import com.jcsantos.testApp
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.math.BigDecimal
import java.util.UUID
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BooksControllerIT {

    @Test
    fun `fetch all books`() = testApp {
        handleRequest(HttpMethod.Get, "/books").apply {
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }
    
    @Test
    fun `registration of a new book`() = testApp {
        handleRequest(HttpMethod.Post, uri = "/books") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(Book(id = UUID.randomUUID(), title = "Teste Julio Mais 1", amount = BigDecimal.TEN).objectToJson())
        }.apply {
            assertEquals(HttpStatusCode.Created, response.status())
        }
    }
}