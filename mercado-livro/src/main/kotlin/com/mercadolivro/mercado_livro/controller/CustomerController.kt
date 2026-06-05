package com.mercadolivro.mercado_livro.controller

import com.mercadolivro.mercado_livro.controller.request.PostCustomerRequest
import com.mercadolivro.mercado_livro.controller.request.PutCustomerRequest
import com.mercadolivro.mercado_livro.model.CustomerModel
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
class CustomerController {
    val customers: MutableList<CustomerModel> = mutableListOf()

    @GetMapping
    fun getAll(@RequestParam name: String?): List<CustomerModel> {
        name?.let {
            return customers.filter { it.name.contains(name, ignoreCase = true) }
        }
        return customers
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): CustomerModel? {
        return customers.firstOrNull { it.id == id }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody customer: PostCustomerRequest): CustomerModel {
        val id: Int = if (customers.isEmpty()) 1 else customers.last().id + 1
        val newCustomer = CustomerModel(
            id,
            customer.name,
            customer.email
        )

        customers.add(newCustomer)

        return CustomerModel(id, newCustomer.name, newCustomer.email)
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Int,
        @RequestBody customer: PutCustomerRequest
    ): CustomerModel? {
        val customerToUpdate = customers.firstOrNull { it.id == id }
        customerToUpdate?.let {
            it.name = customer.name
            it.email = customer.email
        }

        return customerToUpdate
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) {
        customers.removeIf { it.id == id }
    }
}