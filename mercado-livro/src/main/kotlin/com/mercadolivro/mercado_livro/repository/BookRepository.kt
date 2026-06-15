package com.mercadolivro.mercado_livro.repository

import com.mercadolivro.mercado_livro.enums.BookStatus
import com.mercadolivro.mercado_livro.model.BookModel
import com.mercadolivro.mercado_livro.model.CustomerModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository


interface BookRepository : JpaRepository<BookModel, Int> {
    fun findByNameContaining(name: String, pageable: Pageable): Page<BookModel>

    fun findByStatus(status: BookStatus, pageable: Pageable): Page<BookModel>

    fun findByCustomer(customer: CustomerModel): List<BookModel>
}