package com.mercadolivro.events.listener

import com.mercadolivro.events.PurchaseEvent
import com.mercadolivro.service.PurchaseService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class GenerateNfeListener(
    private val purchaseService: PurchaseService
) {
    @Async
    @EventListener
    fun listen(event: PurchaseEvent) {
        println("Generating NFe for purchase: ${event.purchaseModel.id}")

        val nfe = UUID.randomUUID().toString()
        println("NFe generated: $nfe")

        val purchaseModel = event.purchaseModel.copy(nfe = nfe)
        println("Updated purchase model with NFe: $purchaseModel")

        purchaseService.update(purchaseModel)
    }
}