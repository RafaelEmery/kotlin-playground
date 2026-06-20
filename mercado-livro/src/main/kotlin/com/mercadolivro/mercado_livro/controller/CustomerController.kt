package com.mercadolivro.mercado_livro.controller

import com.mercadolivro.mercado_livro.controller.request.PostCustomerRequest
import com.mercadolivro.mercado_livro.controller.request.PutCustomerRequest
import com.mercadolivro.mercado_livro.controller.response.CustomerResponse
import com.mercadolivro.mercado_livro.extension.toCustomerModel
import com.mercadolivro.mercado_livro.extension.toResponse
import com.mercadolivro.mercado_livro.service.CustomerService
import jakarta.validation.Valid
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
@RequestMapping("/customers")
class CustomerController(
    val service: CustomerService
) {
    @GetMapping
    fun getAll(@RequestParam name: String?): List<CustomerResponse> {
        return service.getAll(name).map { it.toResponse() }
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): CustomerResponse {
        return service.getById(id).toResponse()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid customer: PostCustomerRequest): CustomerResponse {
        return service.create(customer.toCustomerModel()).toResponse()
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Int,
        @RequestBody @Valid customer: PutCustomerRequest
    ): CustomerResponse? {
        val customerSaved = service.getById(id)
        return service.update(customer.toCustomerModel(customerSaved))
            ?.toResponse()
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) {
        service.softDelete(id)
    }
}