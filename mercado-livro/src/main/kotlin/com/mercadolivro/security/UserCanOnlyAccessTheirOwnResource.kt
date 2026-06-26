package com.mercadolivro.security

import org.springframework.security.access.prepost.PreAuthorize

/**
 * Custom annotation to restrict access to a resource based on the user's role and ID.
 * This annotation can be applied to methods or classes to enforce that only users with the 'Admin' role
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@PreAuthorize("hasRole('Admin') or #id == authentication.principal.id")
annotation class UserCanOnlyAccessTheirOwnResource()
