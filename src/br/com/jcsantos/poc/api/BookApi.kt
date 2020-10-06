package com.jcsantos.br.com.jcsantos.poc.api

import com.jcsantos.br.com.jcsantos.poc.domain.Book
import com.jcsantos.br.com.jcsantos.poc.service.BookService
import de.nielsfalk.ktor.swagger.created
import de.nielsfalk.ktor.swagger.description
import de.nielsfalk.ktor.swagger.example
import de.nielsfalk.ktor.swagger.examples
import de.nielsfalk.ktor.swagger.post
import de.nielsfalk.ktor.swagger.responds
import de.nielsfalk.ktor.swagger.version.shared.Group
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.locations.Location
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import mu.KotlinLogging
import java.math.BigDecimal

fun Route.book(service: BookService) {
    
    val logger = KotlinLogging.logger {}
    
    route("/v1") {
        post<books, BookModel>(
            "create".description("Save a Book").examples(example("Book", BookModel.EXAMPLE))
                .responds(
                    created<BookModel>(
                        example("response", BookModel.EXAMPLE_RESPONSE)
                    )
                )
        ) { _, data ->
            try {
               val book = Book(title = data.title, amount = data.amount)
                service.create(book = book)
                call.respond(HttpStatusCode.Created, book)
            } catch (ex: Exception) {
                logger.error("Failed to perform insert", ex)
            }
        }
        
        get {
            call.respond(HttpStatusCode.OK, service.getAll())
        }
    }
}

@Group("Book Operations")
@Location("/books")
class books

data class BookModel(val title: String, val amount: BigDecimal) {
    companion object {
        val EXAMPLE = mapOf(
            "title" to "Migrating to Kotlin",
            "amount" to "55.70"
        )
        
        val EXAMPLE_RESPONSE = mapOf(
            "id" to "e9995968-bb70-4d4b-9b00-02af71cc67d9",
            "title" to "Migrating to Kotlin",
            "amount" to "55.70",
            "version" to "1"
        )
    }
}