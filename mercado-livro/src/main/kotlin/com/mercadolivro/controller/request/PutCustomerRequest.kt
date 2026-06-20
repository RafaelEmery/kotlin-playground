package com.mercadolivro.controller.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty

data class PutCustomerRequest(
    @field:NotEmpty(message = "The name is required")
    var name: String,

    @field:NotEmpty(message = "The email is required")
    @field:Email(message = "The email is invalid")
    var email: String
)
