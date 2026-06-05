package com.mercadolivro.mercado_livro.extension

import com.mercadolivro.mercado_livro.model.CustomerModel
import com.mercadolivro.mercado_livro.controller.request.PostCustomerRequest
import com.mercadolivro.mercado_livro.controller.request.PutCustomerRequest

fun PostCustomerRequest.toCustomerModel(): CustomerModel {
    return CustomerModel(null, this.name, this.email)
}

fun PutCustomerRequest.toCustomerModel(id: Int): CustomerModel {
    return CustomerModel(id, this.name, this.email)
}