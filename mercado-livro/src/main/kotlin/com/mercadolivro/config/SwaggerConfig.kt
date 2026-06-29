package com.mercadolivro.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

/**
 * Configuration class for Swagger/OpenAPI documentation.
 *
 * @Operation is used at the controller level to document each endpoint,
 * while @OpenAPIDefinition is used at the configuration level to provide overall API information.
 * @Operation has summary and description attributes for endpoint documentation,
 * while @OpenAPIDefinition has info attribute for API metadata.
 */
@Configuration
@Profile("!prod") // Enable Swagger only in non-production profiles
@OpenAPIDefinition(
    info = Info(
        title = "Mercado Livro API",
        version = "1.0.0",
        description = "API for books marketplace"
    )
)
class SwaggerConfig