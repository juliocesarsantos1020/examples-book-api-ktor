package com.jcsantos.br.com.jcsantos.poc.service

import com.jcsantos.br.com.jcsantos.poc.domain.Book
import com.jcsantos.br.com.jcsantos.poc.repository.BookRepository
import java.util.UUID

class BookService(private val repository: BookRepository) {
    
    fun create(book: Book) {
        repository.create(book)
    }
    
    fun read(bookId: UUID): Book? {
        return repository.read(bookId)
    }
    
    fun update(book: Book, bookId: UUID) {
        repository.update(book, bookId)
    }
    
    fun delete(bookId: UUID) {
        repository.delete(bookId)
    }
    
    fun getAll(): ArrayList<Book> {
        return repository.getAll()
    }
}