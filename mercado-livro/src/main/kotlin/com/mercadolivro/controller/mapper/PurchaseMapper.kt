package com.mercadolivro.controller.mapper

import com.mercadolivro.controller.request.PostPurchaseRequest
import com.mercadolivro.model.PurchaseModel
import com.mercadolivro.service.BookService
import com.mercadolivro.service.CustomerService
import com.mercadolivro.service.PurchaseService
import org.springframework.stereotype.Component

/**
 * Using a mapper with `toModel` as alternative for ConverterExtensionFunction at extension
 * Mapper is Service Mapper and is responsible for converting the request to the model,
 * and it can also be used to convert the model to the response if needed.
 *
 * Converter extension function is more suitable for simple conversions and Kotlin idiomatic code,
 * while a mapper class is more suitable for complex conversions that require
 * additional logic or dependencies.
 */
@Component
class PurchaseMapper(
    private val purchaseService: PurchaseService,
    private val bookService: BookService,
    private val customerService: CustomerService
) {
    fun toModel(request: PostPurchaseRequest): PurchaseModel {
        val customer = customerService.getById(request.customerId)
        val books = bookService.getAllByIds(request.bookIds)
        val purchase = PurchaseModel(
            customer = customer,
            books = books.toMutableList(),
            price = books.sumOf { it.price }
        )

        purchaseService.validatePurchase(purchase)

        return purchase
    }
}