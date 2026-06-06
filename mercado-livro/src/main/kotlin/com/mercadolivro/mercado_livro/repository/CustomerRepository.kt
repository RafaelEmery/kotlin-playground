package com.mercadolivro.mercado_livro.repository

import com.mercadolivro.mercado_livro.model.CustomerModel
import org.springframework.data.repository.CrudRepository

interface CustomerRepository : CrudRepository<CustomerModel, Int> {
    /**
     * Spring Data JPA will automatically generate the implementation of this method based on the method name.
     *
     * So findByNameContaining will generate a query that will search for customers
     * whose name contains the given string.
     * Could be findByName or findByNameLike, but findByNameContaining is more intuitive and easier to understand.
     */
    fun findByNameContaining(name: String): List<CustomerModel>
}