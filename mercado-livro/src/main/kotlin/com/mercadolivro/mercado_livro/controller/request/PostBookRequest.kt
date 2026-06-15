package com.mercadolivro.mercado_livro.controller.request

import com.fasterxml.jackson.annotation.JsonAlias

data class PostBookRequest(
    val name: String,
    val price: Double,

    @JsonAlias("customer_id")
    val customerId: Int
)
