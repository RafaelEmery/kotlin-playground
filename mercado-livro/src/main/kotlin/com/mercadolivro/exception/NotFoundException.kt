package com.mercadolivro.exception

/**
 * Creating a custom exception to handle the case when a resource is not found in the database.
 *
 * The override of the message is because the base Exception (extended by NotFoundException)
 * has a default message, but we want to customize it to be more specific to our application.
 */
class NotFoundException(
    override val message: String,
    val errorCode: String
): Exception() {
}