package com.mercadolivro.mercado_livro.controller.response

import com.mercadolivro.mercado_livro.enums.CustomerStatus

data class CustomerResponse(
    val id: Int?,
    val name: String,
    val email: String,
    val status: CustomerStatus
)
