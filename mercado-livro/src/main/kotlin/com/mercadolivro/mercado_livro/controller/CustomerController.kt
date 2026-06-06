package com.mercadolivro.mercado_livro.controller

import com.mercadolivro.mercado_livro.controller.request.PostCustomerRequest
import com.mercadolivro.mercado_livro.controller.request.PutCustomerRequest
import com.mercadolivro.mercado_livro.extension.toCustomerModel
import com.mercadolivro.mercado_livro.model.CustomerModel
import com.mercadolivro.mercado_livro.service.CustomerService
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
    fun getAll(@RequestParam name: String?): List<CustomerModel> {
        return service.getAll(name)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): CustomerModel {
        return service.getById(id)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody customer: PostCustomerRequest): CustomerModel {
        return service.create(customer.toCustomerModel())
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Int,
        @RequestBody customer: PutCustomerRequest
    ): CustomerModel? {
        return service.update(customer.toCustomerModel(id))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) {
        service.delete(id)
    }
}