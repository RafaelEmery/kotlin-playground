package com.mercadolivro.security

import com.mercadolivro.enums.Errors
import com.mercadolivro.exception.AuthenticationException
import com.mercadolivro.service.UserDetailsCustomService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

class AuthorizationFilter(
    authenticationManager: AuthenticationManager,
    private val userDetailsService: UserDetailsCustomService,
    private val jwtUtil: JwtUtil
) : BasicAuthenticationFilter(authenticationManager) {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val authorizationHeader = request.getHeader("Authorization")
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            val auth = getAuthentication(authorizationHeader.split(" ")[1])
            SecurityContextHolder.getContext().authentication = auth

        }

        chain.doFilter(request, response)
    }

    private fun getAuthentication(token: String): UsernamePasswordAuthenticationToken {
        if (!jwtUtil.isValidToken(token)) {
            throw AuthenticationException(
                Errors.TOKEN_ERROR.message,
                Errors.TOKEN_ERROR.code
            )
        }
        val subject = jwtUtil.getSubject(token)
        val customer: UserDetails = userDetailsService.loadUserByUsername(subject)
        return UsernamePasswordAuthenticationToken(
            customer,
            null,
            customer.authorities
        )
    }
}