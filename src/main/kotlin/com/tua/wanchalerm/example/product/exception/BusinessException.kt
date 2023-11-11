package com.tua.wanchalerm.example.product.exception

import org.springframework.http.HttpStatus

class BusinessException(
    override var message: String?,
    var description: String? = null,
    var httpStatus: HttpStatus = HttpStatus.CONFLICT,
    throwable: Throwable? = null
) : RuntimeException(message, throwable)
