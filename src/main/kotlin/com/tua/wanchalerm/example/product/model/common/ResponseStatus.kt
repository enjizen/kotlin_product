package com.tua.wanchalerm.example.product.model.common

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ResponseStatus(
    val message: String? = null,
    val description: String? = null,
    )
