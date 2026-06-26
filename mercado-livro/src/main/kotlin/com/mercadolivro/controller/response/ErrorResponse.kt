package com.mercadolivro.controller.response

import com.fasterxml.jackson.annotation.JsonAlias

data class ErrorResponse(
    @JsonAlias("http_code")
    val httpCode: Int,
    val message: String,
    @JsonAlias("internal_code")
    val internalCode: String,
    val errors: List<FieldErrorResponse>
)
