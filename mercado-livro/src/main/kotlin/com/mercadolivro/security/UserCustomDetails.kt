package com.mercadolivro.security

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.model.CustomerModel
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

/**
 * UserCustomDetails class implements the UserDetails interface to provide user authentication details.
 *
 * Extends the UserDetails interface from Spring Security to provide custom user details for authentication
 * and be used as UserDetails on UserDetailsCustomerService.
 *
 * It takes a CustomerModel object as a parameter and provides methods to retrieve the user's authorities,
 * password, username, and account status. The authorities are derived from the customer's roles, and the
 * account status is determined by the customer's status.
 */
class UserCustomDetails(val customerModel: CustomerModel) : UserDetails {
    val id = customerModel.id

    override fun getAuthorities() = customerModel.roles.map { SimpleGrantedAuthority(it.description) }.toMutableList()

    override fun getPassword() = customerModel.password

    override fun getUsername() = customerModel.id.toString()

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled(): Boolean = customerModel.status == CustomerStatus.ACTIVE
}