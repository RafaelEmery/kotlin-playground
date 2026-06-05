package com.mercadolivro.mercado_livro.service

import com.mercadolivro.mercado_livro.controller.request.PostCustomerRequest
import com.mercadolivro.mercado_livro.controller.request.PutCustomerRequest
import com.mercadolivro.mercado_livro.model.CustomerModel
import org.springframework.stereotype.Service


@Service
class CustomerService {
    val customers: MutableList<CustomerModel> = mutableListOf()

    fun getAll(name: String?): List<CustomerModel> {
        name?.let {
            return customers.filter { it.name.contains(name, ignoreCase = true) }
        }
        return customers
    }

    fun getById(id: Int): CustomerModel? {
        return customers.firstOrNull { it.id == id }
    }

    fun create(customer: CustomerModel): CustomerModel {
        val id: Int = if (customers.isEmpty()) 1
            else customers.last().id?.plus(1) ?: 1
        customer.id = id

        customers.add(customer)

        return customer
    }

    fun update(customer: CustomerModel): CustomerModel? {
        val customerToUpdate = customers.firstOrNull { it.id == customer.id }
        customerToUpdate?.let {
            it.name = customer.name
            it.email = customer.email
        }

        return customerToUpdate
    }

    fun delete(id: Int) {
        customers.removeIf { it.id == id }
    }
}