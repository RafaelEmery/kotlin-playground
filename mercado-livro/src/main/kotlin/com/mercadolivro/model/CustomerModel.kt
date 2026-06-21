package com.mercadolivro.model

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.enums.Profile
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn

@Entity(name = "customer")
data class CustomerModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: CustomerStatus,

    @Column
    val password: String? = null,

    /**
     * The customer_roles is the name of the table that will be created to store the roles of the customers,
     * and customer_id is the name of the column that will be created in the customer_roles. Since there's no
     * primary key in the customer_roles table, we need to specify the join column to link it to the customer table.
     *
     * FIXME: needs FLyway to be working to create customer_roles table
     *
     * Using @CollectionTable to map a collection of basic types
     * (Set<Profile>) to a separate table (customer_roles).
     * Using @ElementCollection to indicate that this is a collection of basic types,
     * and @Enumerated to specify that the enum values should be stored as strings in the database.
     */
    @Column(name = "roles")
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "customer_roles", joinColumns = [JoinColumn(name = "customer_id")])
    @ElementCollection(targetClass = Profile::class, fetch = FetchType.EAGER)
    var roles: Set<Profile> = setOf()
)