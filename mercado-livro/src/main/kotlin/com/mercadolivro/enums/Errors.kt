package com.mercadolivro.enums

enum class Errors(val code: String, val message: String) {
    BOOK_NOT_FOUND("ML-001", "Book [%s] not found"),
    BOOK_BAD_REQUEST("ML-002", "Book has invalid data [%S]"),
    CUSTOMER_NOT_FOUND("ML-010", "Customer [%s] not found"),
    CUSTOMER_BAD_REQUEST("ML-011", "Customer has invalid data [%S]"),
    PURCHASE_BAD_REQUEST("ML-020", "Purchase has invalid data: [%s]"),
    AUTHENTICATION_ERROR("ML-030", "Invalid email or password"),
}