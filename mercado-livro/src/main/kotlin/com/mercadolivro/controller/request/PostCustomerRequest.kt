package com.mercadolivro.controller.request

import com.mercadolivro.validation.EmailAvailable
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty

data class PostCustomerRequest(
    /*
    * Using validations annotations from jakarta.validation.constraints to validate the request body.
    * Must use with @Valid on the controller method parameter to work.
    *
    * More constraints example here: https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/constraints/package-summary
    * */
    @field:NotEmpty(message = "The name is required")
    var name: String,

    /**
     * Using custom validation annotation to validate the email availability.
     * Defined at validation.EmailAvailable.kt and implemented at validation.EmailAvailableValidator.kt
     */
    @field:NotEmpty(message = "The email is required")
    @field:Email(message = "The email is invalid")
    @EmailAvailable
    var email: String,

    @field:NotEmpty(message = "The password is required")
    var password: String
)
