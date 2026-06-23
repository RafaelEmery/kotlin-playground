package com.mercadolivro.service

import com.mercadolivro.enums.Errors
import com.mercadolivro.exception.AuthenticationException
import com.mercadolivro.repository.CustomerRepository
import com.mercadolivro.security.UserCustomDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

/**
 * Service class that implements the UserDetailsService interface to load user details for authentication.
 *
 * It retrieves a customer from the database using the provided ID and returns a UserCustomDetails object
 * containing the customer's information. If the customer is not found, it throws an AuthenticationException.
 */
@Service
class UserDetailsCustomService(
    private val customerRepository: CustomerRepository
) : UserDetailsService {
    override fun loadUserByUsername(id: String): UserDetails {
        val customer = customerRepository.findById(id.toInt())
            .orElseThrow { AuthenticationException(
                Errors.AUTHENTICATION_ERROR.message,
                Errors.AUTHENTICATION_ERROR.code
            ) }

        return UserCustomDetails(customer)
    }
}