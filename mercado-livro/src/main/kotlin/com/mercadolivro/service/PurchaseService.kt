package com.mercadolivro.service

import com.mercadolivro.events.PurchaseEvent
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

    fun update(purchaseModel: PurchaseModel) {
        repository.save(purchaseModel)
    }
}