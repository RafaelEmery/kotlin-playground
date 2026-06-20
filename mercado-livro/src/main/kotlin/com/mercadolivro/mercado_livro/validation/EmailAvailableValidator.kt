package com.mercadolivro.mercado_livro.validation

import com.mercadolivro.mercado_livro.service.CustomerService
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

/**
 * EmailAvailableValidator is a custom validator that checks if the email is available or not.
 * It implements ConstraintValidator interface and overrides the isValid method to implement the validation logic.
 */
class EmailAvailableValidator(
    val customerService: CustomerService
): ConstraintValidator<EmailAvailable, String> {
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        // Here you can implement the logic to check if the email is available or not.
        // For example, you can query the database to see if the email already exists.
        // Return true if the email is available, false otherwise.
        if (value.isNullOrEmpty()) {
            return false // NotEmpty validation will handle this case, so we can return false here.
        }
        return customerService.isEmailAvailable(value)
    }
}