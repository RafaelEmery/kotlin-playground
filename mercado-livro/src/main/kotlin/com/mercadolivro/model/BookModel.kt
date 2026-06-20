package com.mercadolivro.model

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.enums.Errors
import com.mercadolivro.exception.BadRequestException
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.EnumType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.math.BigDecimal

@Entity(name = "book")
data class BookModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int?,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false, unique = true)
    var price: BigDecimal,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer: CustomerModel? = null
) {
    /**
     * Status as class property with custom setter
     * to validate if status is not CANCELLED or DELETED.
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: BookStatus? = null
        set(value) {
            if (field == BookStatus.CANCELLED || field == BookStatus.DELETED)
                throw BadRequestException(
                    Errors.BOOK_BAD_REQUEST.message.format(field),
                    Errors.BOOK_BAD_REQUEST.code
                )

            field = value
        }

    /**
     * Secondary constructor to initialize all properties, including status,
     * since the primary constructor (on data class parameters) does not include status.
     */
    constructor(
        id: Int? = null,
        name: String,
        price: BigDecimal,
        status: BookStatus?,
        customer: CustomerModel? = null
    ) : this(id, name, price, customer) {
        this.status = status
    }
}