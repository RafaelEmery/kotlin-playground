package com.mercadolivro.mercado_livro.controller.response

import com.mercadolivro.mercado_livro.enums.BookStatus
import com.mercadolivro.mercado_livro.model.CustomerModel
import java.math.BigDecimal

data class BookResponse(
    val id: Int?,
    val name: String,
    val price: BigDecimal,
    val status: BookStatus? = null,
    val customer: CustomerModel? = null
)
