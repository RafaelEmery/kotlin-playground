package com.mercadolivro.config

import com.mercadolivro.enums.Role
import com.mercadolivro.repository.CustomerRepository
import com.mercadolivro.security.AuthenticationFilter
import com.mercadolivro.security.AuthorizationFilter
import com.mercadolivro.security.CustomAuthenticationEntryPoint
import com.mercadolivro.security.JwtUtil
import com.mercadolivro.service.UserDetailsCustomService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

/**
 * Using @EnableMethodSecurity(prePostEnabled = true) to enable method-level
 * security annotations like @PreAuthorize and @PostAuthorize (on controllers).
 */
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig {
    private val PUBLIC_ROUTES = arrayOf(
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/v3/api-docs/**",
        "/customers/**",
        "/books/**",
    )
    private val ADMIN_ROUTES = arrayOf(
        "/admin/**"
    )

    /**
     * securityFilterChain bean is responsible for configuring the security settings for the application.
     *      * It disables CSRF protection, allows public access to specified URLs, and requires authentication
     *      * for all other requests. It also enables HTTP Basic authentication.
     *
     * The authorizeHttpRequests method is used to define the authorization rules for incoming HTTP requests.
     * The requestMatchers method is used to specify the URLs that should be publicly accessible, while
     * the anyRequest method is used to specify that all other requests require authentication.
     */
    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        authenticationConfiguration: AuthenticationConfiguration,
        customerRepository: CustomerRepository,
        jwtUtil: JwtUtil,
        entryPoint: CustomAuthenticationEntryPoint
    ): SecurityFilterChain {
        val authenticationManager = authenticationConfiguration.authenticationManager
        val authenticationFilter = AuthenticationFilter(authenticationManager, customerRepository, jwtUtil)
        val authorizationFilter = AuthorizationFilter(authenticationManager, UserDetailsCustomService(customerRepository), jwtUtil)

        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                // Using * to pass the list as strings
                it.requestMatchers(*PUBLIC_ROUTES).permitAll()
                it.requestMatchers(*ADMIN_ROUTES).hasRole(Role.ADMIN.description)
                it.anyRequest().authenticated()
            }
            .addFilterAt(authenticationFilter, AuthenticationFilter::class.java)
            .addFilterAt(authorizationFilter, AuthorizationFilter::class.java)
            .exceptionHandling { it.authenticationEntryPoint(entryPoint) }
            .httpBasic(Customizer.withDefaults())

        return http.build()
    }

    @Bean
    fun bCryptPasswordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun authenticationProvider(
        userDetailsCustomService: UserDetailsCustomService,
        passwordEncoder: BCryptPasswordEncoder
    ): DaoAuthenticationProvider {
        val provider = DaoAuthenticationProvider(userDetailsCustomService)
        provider.setPasswordEncoder(passwordEncoder)
        return provider
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration().apply {
            allowedOriginPatterns = listOf("http://localhost:*", "http://127.0.0.1:*")
            allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
            allowedHeaders = listOf("*")
            allowCredentials = true
            exposedHeaders = listOf("Authorization")
        }

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}