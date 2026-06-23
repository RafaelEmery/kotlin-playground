package com.mercadolivro.security

import com.mercadolivro.controller.request.LoginRequest
import com.mercadolivro.enums.Errors
import com.mercadolivro.exception.AuthenticationException
import com.mercadolivro.repository.CustomerRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import tools.jackson.module.kotlin.jacksonObjectMapper

/**
 * Note about dependency injection in extending classes:
 *
 * Using
 * UsernamePasswordAuthenticationFilter(authenticationManager)
 *
 * Is equal to
 * AuthenticationFilter(authenticationManager) {
 *    super(authenticationManager) //
 * }
 */
class AuthenticationFilter(
    authenticationManager: AuthenticationManager,
    private val customerRepository: CustomerRepository,
    private val jwtUtil: JwtUtil
) : UsernamePasswordAuthenticationFilter(authenticationManager) {
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        try {
            // readValue needs .java conversion because it is a Java method, not a Kotlin method
            // If it was a Kotlin method, we could use LoginRequest::class instead of LoginRequest::class.java
            // and the type would be a KClass instead of a Class
            val loginRequest = jacksonObjectMapper().readValue(request.inputStream, LoginRequest::class.java)
            val customerId = customerRepository.findByEmail(loginRequest.email)?.id

            val token = UsernamePasswordAuthenticationToken(
                customerId,
                loginRequest.password
            )
            return authenticationManager.authenticate(token)
        } catch (e: Exception) {
            throw AuthenticationException(
                Errors.AUTHENTICATION_ERROR.message,
                Errors.AUTHENTICATION_ERROR.code
            )
        }
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        // principal is the authenticated user, which is an instance of UserCustomDetails
        val userId = (authResult.principal as UserCustomDetails).id
        val token = jwtUtil.generateToken(userId!!)

        response.addHeader("Authorization", "Bearer $token")
        response.addHeader("userId", userId.toString())
    }
}
