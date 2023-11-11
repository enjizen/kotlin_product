package com.tua.wanchalerm.example.product.exception

import org.springframework.http.HttpStatus

class DataNotFoundException (
    override var message: String?,
    var description: String? = null,
    var httpStatus: HttpStatus = HttpStatus.NOT_FOUND,
    throwable: Throwable? = null
) : RuntimeException(message, throwable)