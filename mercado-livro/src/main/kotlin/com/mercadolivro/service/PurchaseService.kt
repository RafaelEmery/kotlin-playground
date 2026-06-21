package com.mercadolivro.service

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.enums.Errors
import com.mercadolivro.events.PurchaseEvent
import com.mercadolivro.exception.BadRequestException
import com.mercadolivro.model.PurchaseModel
import com.mercadolivro.repository.PurchaseRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class PurchaseService(
    private val repository: PurchaseRepository,
    private val publisher: ApplicationEventPublisher
) {
    fun create(purchaseModel: PurchaseModel) {
        repository.save(purchaseModel)

        // Using the event publisher to publish a PurchaseEvent when a new purchase is created
        publisher.publishEvent(PurchaseEvent(this, purchaseModel))
    }

    fun validatePurchase(purchaseModel: PurchaseModel) {
        val invalidBookStatus = setOf(BookStatus.DELETED, BookStatus.CANCELLED)
        purchaseModel.books.forEach {
            if (it.status!! in invalidBookStatus) {
                throw BadRequestException(
                    Errors.PURCHASE_BAD_REQUEST.message.format("Book ${it.id} with status ${it.status}"),
                    Errors.PURCHASE_BAD_REQUEST.code
                )
            }
        }

        if (purchaseModel.customer.status == CustomerStatus.INACTIVE) {
            throw BadRequestException(
                Errors.PURCHASE_BAD_REQUEST.message.format("Customer ${purchaseModel.customer.id} is inactive"),
                Errors.PURCHASE_BAD_REQUEST.code
            )
        }
    }

    fun update(purchaseModel: PurchaseModel) {
        repository.save(purchaseModel)
    }
}