package com.mercadolivro.mercado_livro.exception

import com.mercadolivro.mercado_livro.controller.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import kotlin.collections.emptyList

@ControllerAdvice
class ControllerAdvice {
    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            httpCode = HttpStatus.NOT_FOUND.value(),
            message = ex.message,
            internalCode = ex.errorCode,
            errors = emptyList()
        )

        return ResponseEntity(error, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(
            httpCode = HttpStatus.BAD_REQUEST.value(),
            message = ex.message,
            internalCode = ex.errorCode,
            errors = emptyList()
        )

        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }
}