package com.tua.wanchalerm.example.product.exception

import org.springframework.http.HttpStatus

class InputValidationException(
    override var message: String?,
    var description: String? = null,
    var httpStatus: HttpStatus = HttpStatus.BAD_REQUEST,
    throwable: Throwable? = null
) : RuntimeException(message, throwable)
