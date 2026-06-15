package com.mercadolivro.mercado_livro.service

import com.mercadolivro.mercado_livro.enums.CustomerStatus
import com.mercadolivro.mercado_livro.model.CustomerModel
import com.mercadolivro.mercado_livro.repository.CustomerRepository
import org.springframework.stereotype.Service


@Service
class CustomerService(
    val repository: CustomerRepository,
    val bookService: BookService
) {
    /**
     * We can use a findAll() and toList() to convert the Iterable returned by findAll()
     * into a List so that we can use the filter function to filter the customers by name.
     *
     * But the findByNameContaining method is more efficient because it will generate
     * a query that will search for customers whose name contains the given string,
     * so it will return only the customers that match the criteria, instead of returning
     * all customers and then filtering them in memory.
     *
     * Docs example: https://www.baeldung.com/spring-jpa-like-queries
     */
    fun getAll(name: String?): List<CustomerModel> {
        return repository.findByNameContaining(name ?: "")
    }

    fun getById(id: Int): CustomerModel {
        return repository.findById(id).orElseThrow()
    }

    fun create(customer: CustomerModel): CustomerModel {
        return repository.save(customer)
    }

    fun update(customer: CustomerModel): CustomerModel? {
        if (!repository.existsById(customer.id!!)) {
            throw Exception("Customer not found")
        }

        return  repository.save(customer)
    }

    fun softDelete(id: Int) {
        val customer = getById(id)

        bookService.softDeleteByCustomer(customer)

        customer.status = CustomerStatus.INACTIVE
        repository.save(customer)
    }
}