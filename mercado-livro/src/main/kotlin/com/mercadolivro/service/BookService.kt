package com.mercadolivro.service

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.enums.Errors
import com.mercadolivro.exception.NotFoundException
import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.BookRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BookService(
    private val repository: BookRepository
) {
    fun getAll(name: String?, pageable: Pageable): Page<BookModel> {
        return repository.findByNameContaining(name ?: "", pageable)
    }

    /**
     * We can only use "=" directly on the function (and no return statement)
     * because has only one line
     */
    fun getAllActive(pageable: Pageable): Page<BookModel> =
        repository.findByStatus(BookStatus.ACTIVE, pageable)

    fun getById(id: Int): BookModel {
        return repository.findById(id)
            .orElseThrow { NotFoundException(
                Errors.BOOK_NOT_FOUND.message.format(id),
                Errors.BOOK_NOT_FOUND.code
            ) }
    }

    fun create(book: BookModel): BookModel {
        return repository.save(book)
    }

    fun update(book: BookModel): BookModel {
        return repository.save(book)
    }

    fun softDelete(id: Int) {
        val book = getById(id)
        book.status = BookStatus.CANCELLED

        repository.save(book)
    }

    fun softDeleteByCustomer(customer: CustomerModel) {
        val books: List<BookModel> = repository.findByCustomer(customer)

        for (book in books) {
            if (book.status != BookStatus.SOLD) {
                book.status = BookStatus.DELETED
            }
        }
        // Saving in batch, instead of one by one, to optimize the performance
        repository.saveAll(books)
    }

    fun getAllByIds(bookIds: List<Int>): List<BookModel> {
        return repository.findAllById(bookIds).toList()
    }

    fun purchase(books: MutableList<BookModel>) {
        for (book in books) {
            book.status = BookStatus.SOLD
        }

        repository.saveAll(books)
    }
}