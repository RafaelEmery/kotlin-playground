package com.mercadolivro.config

import com.mercadolivro.repository.CustomerRepository
import com.mercadolivro.security.AuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {
    private val PUBLIC_ROUTES = arrayOf(
        "/swagger-ui/**",
        "/v3/api-docs/**"
    )

    /**
     * securityFilterChain bean is responsible for configuring the security settings for the application.
     * It disables CSRF protection, allows public access to specified URLs, and requires authentication
     * for all other requests. It also enables HTTP Basic authentication.
     *
     * The authorizeHttpRequests method is used to define the authorization rules for incoming HTTP requests.
     * The requestMatchers method is used to specify the URLs that should be publicly accessible, while
     * the anyRequest method is used to specify that all other requests require authentication.
     */
    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        authenticationConfiguration: AuthenticationConfiguration,
        customerRepository: CustomerRepository
    ): SecurityFilterChain {
        val authenticationManager = authenticationConfiguration.authenticationManager
        val authenticationFilter = AuthenticationFilter(authenticationManager, customerRepository)

        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                // Using * to pass the list as strings
                it.requestMatchers(*PUBLIC_ROUTES).permitAll()
                it.anyRequest().authenticated()
            }
            .addFilterAt(authenticationFilter, AuthenticationFilter::class.java)
            .httpBasic(Customizer.withDefaults())

        return http.build()
    }

    @Bean
    fun bCryptPasswordEncoder() = BCryptPasswordEncoder()
}