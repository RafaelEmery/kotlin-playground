package com.mercadolivro.enums

enum class Errors(val code: String, val message: String) {
    BOOK_NOT_FOUND("ML-001", "Book [%s] not found"),
    BOOK_BAD_REQUEST("ML-002", "Book has invalid data [%S]"),
    CUSTOMER_NOT_FOUND("ML-010", "Customer [%s] not found"),
    CUSTOMER_BAD_REQUEST("ML-011", "Customer has invalid data [%S]"),
}