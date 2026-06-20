package com.mercadolivro.events

import com.mercadolivro.model.PurchaseModel
import org.springframework.context.ApplicationEvent

/**
 * PurchaseEvent can have multiple listeners, such as:
 * - GenerateNfeListener: Generates an NFe for the purchase and updates the purchase model with the generated NFe.
 * - UpdateSoldBookListener: Updates the stock of the books that were purchased, marking them as sold.
 *
 * Other examples:
 * - SendEmailListener: Sends a confirmation email to the customer about the purchase.
 * - LogPurchaseListener: Logs the purchase details for auditing and analytics purposes.
 * - UpdateCustomerPointsListener: Updates the customer's loyalty points based on the purchase amount.
 * - NotifyWarehouseListener: Notifies the warehouse to prepare the shipment for the purchased items.
 * - UpdateSalesReportListener: Updates the sales report with the new purchase
 */
class PurchaseEvent(
    source: Any,
    val purchaseModel: PurchaseModel
): ApplicationEvent(source)