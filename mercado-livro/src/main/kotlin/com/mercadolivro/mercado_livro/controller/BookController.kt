package com.mercadolivro.mercado_livro.controller

import com.mercadolivro.mercado_livro.controller.request.PostBookRequest
import com.mercadolivro.mercado_livro.controller.request.PutBookRequest
import com.mercadolivro.mercado_livro.controller.response.BookResponse
import com.mercadolivro.mercado_livro.service.BookService
import com.mercadolivro.mercado_livro.service.CustomerService
import com.mercadolivro.mercado_livro.extension.toBookModel
import com.mercadolivro.mercado_livro.extension.toResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/books")
class BookController(
    val service: BookService,
    val customerService: CustomerService
) {
    @GetMapping
    fun getAll(
        @RequestParam name: String?,
        @PageableDefault(size = 10, page = 0) pageable: Pageable
    ): Page<BookResponse> {
        return service.getAll(name, pageable).map { it.toResponse() }
    }

    @GetMapping("/active")
    fun getActive(@PageableDefault(size = 10, page = 0) pageable: Pageable): Page<BookResponse> {
        return service.getAllActive(pageable).map { it.toResponse() }
    }

    @GetMapping("/{id}")
    fun getById(@RequestParam id: Int): BookResponse {
        return service.getById(id).toResponse()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody book: PostBookRequest): BookResponse {
        val customer = customerService.getById(book.customerId)

        return service.create(book.toBookModel(customer)).toResponse()
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody book: PutBookRequest): BookResponse {
        val savedBook = service.getById(id)

        return service.update(book.toBookModel(savedBook))
            .toResponse()
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) {
        service.softDelete(id)
    }
}