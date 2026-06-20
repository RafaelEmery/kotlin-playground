package com.mercadolivro.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class PostBookRequest(
    @field:NotEmpty(message = "The name is required")
    val name: String,

    @field:NotNull(message = "The price is required")
    val price: Double,

    @JsonAlias("customer_id")
    val customerId: Int
)
