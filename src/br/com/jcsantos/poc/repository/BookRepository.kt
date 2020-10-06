package com.jcsantos.br.com.jcsantos.poc.repository

import com.jcsantos.br.com.jcsantos.poc.domain.Book
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.math.BigDecimal
import java.util.UUID

private object Books : Table(name = "books") {
    val bookId: Column<UUID> = uuid("book_id").primaryKey()
    val bookTitle: Column<String> = varchar("book_title", 100)
    val amount: Column<BigDecimal> = decimal("amount", 10,2)
    val version: Column<Int> = integer("version")
}

class BookRepository {
    
    fun create(book: Book) {
        transaction {
            Books.insert {
                it[bookId] = book.id
                it[bookTitle] = book.title
                it[amount] = book.amount
                it[version] = book.version
            }
        }
    }
    
    fun read(bookId: UUID): Book? {
        return transaction {
            Books.select {
                Books.bookId eq bookId
            }.map {
                Book(
                    id = it[Books.bookId],
                    title = it[Books.bookTitle],
                    amount = it[Books.amount],
                    version = it[Books.version]
                )
            }.firstOrNull()
        }
    }
    
    fun update(book: Book, bookId: UUID) {
        transaction {
            Books.update({ Books.bookId eq bookId }) {
                it[bookTitle] = book.title
                it[amount] = book.amount
            }
        }
    }
    
    fun delete(bookId: UUID) {
        transaction {
            Books.deleteWhere { Books.bookId eq bookId }
        }
    }
    
    fun getAll(): ArrayList<Book> {
        val books: ArrayList<Book> = arrayListOf()
        
        transaction {
            Books.selectAll().map {
                books.add(
                    Book(
                        id = it[Books.bookId],
                        title = it[Books.bookTitle],
                        amount = it[Books.amount],
                        version = it[Books.version]
                    )
                )
            }
        }
        return books
    }
}