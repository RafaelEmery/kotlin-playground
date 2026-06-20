package com.mercadolivro.validation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

/**
 * Constraint annotation calls EmailAvailableValidator to validate the email availability.
 * Retention is RUNTIME because we want to validate the email at runtime.
 * Target is FIELD because we want to validate the email field in the request body.
 */
@Constraint(validatedBy = [EmailAvailableValidator::class])
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class EmailAvailable(
    val message: String = "The email is already in use",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
