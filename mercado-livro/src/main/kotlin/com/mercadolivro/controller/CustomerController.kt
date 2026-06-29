package com.mercadolivro.controller

import com.mercadolivro.controller.request.PostCustomerRequest
import com.mercadolivro.controller.request.PutCustomerRequest
import com.mercadolivro.controller.response.CustomerResponse
import com.mercadolivro.controller.response.PageResponse
import com.mercadolivro.extension.toCustomerModel
import com.mercadolivro.extension.toPageResponse
import com.mercadolivro.extension.toResponse
import com.mercadolivro.security.UserCanOnlyAccessTheirOwnResource
import com.mercadolivro.service.CustomerService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/customers")
class CustomerController(
    private val service: CustomerService
) {
    @GetMapping
    fun getAll(@RequestParam name: String?): PageResponse<CustomerResponse> {
        return service.getAll(name).map { it.toResponse() }.toPageResponse()
    }

    @GetMapping("/{id}")
    @UserCanOnlyAccessTheirOwnResource
    fun getById(@PathVariable id: Int): CustomerResponse {
        return service.getById(id).toResponse()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid customer: PostCustomerRequest): CustomerResponse {
        return service.create(customer.toCustomerModel()).toResponse()
    }

    @PutMapping("/{id}")
    @UserCanOnlyAccessTheirOwnResource
    fun update(
        @PathVariable id: Int,
        @RequestBody @Valid customer: PutCustomerRequest
    ): CustomerResponse? {
        val customerSaved = service.getById(id)
        return service.update(customer.toCustomerModel(customerSaved))
            ?.toResponse()
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @UserCanOnlyAccessTheirOwnResource
    fun delete(@PathVariable id: Int) {
        service.softDelete(id)
    }
}