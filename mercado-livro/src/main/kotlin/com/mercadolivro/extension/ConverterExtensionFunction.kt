package com.mercadolivro.extension

import com.mercadolivro.controller.request.PostBookRequest
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.controller.request.PostCustomerRequest
import com.mercadolivro.controller.request.PutBookRequest
import com.mercadolivro.controller.request.PutCustomerRequest
import com.mercadolivro.controller.response.BookResponse
import com.mercadolivro.controller.response.CustomerResponse
import com.mercadolivro.enums.BookStatus
import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.model.BookModel

fun PostCustomerRequest.toCustomerModel(): CustomerModel {
    return CustomerModel(null, this.name, this.email, CustomerStatus.ACTIVE)
}

fun PutCustomerRequest.toCustomerModel(previuousValue: CustomerModel): CustomerModel {
    return CustomerModel(previuousValue.id, this.name, this.email, previuousValue.status)
}

fun PostBookRequest.toBookModel(customer: CustomerModel): BookModel {
    return BookModel(
        null,
        this.name,
        this.price.toBigDecimal(),
        BookStatus.ACTIVE,
        customer
    )
}

/**
 * Using "Elvis" operator to update only the fields that are not null in the request,
 * keeping the previous values for the fields that are null.
 *
 * previousValue is the current value of the book in the database,
 * and we use it to keep the values that are not being updated.
 */
fun PutBookRequest.toBookModel(previousValue: BookModel): BookModel {
    return BookModel(
        id = previousValue.id,
        this.name ?: previousValue.name,
        this.price ?: previousValue.price,
        previousValue.status,
        previousValue.customer
    )
}

fun CustomerModel.toResponse(): CustomerResponse {
    return CustomerResponse(this.id, this.name, this.email, this.status)
}

fun BookModel.toResponse(): BookResponse {
    return BookResponse(
        this.id,
        this.name,
        this.price,
        this.status,
        this.customer
    )
}