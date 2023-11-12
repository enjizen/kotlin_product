package com.tua.wanchalerm.example.product.model.request

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.math.BigDecimal

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ProductRequest (
    val name: String? = null,
    val description: String? = null,
    val price: BigDecimal? = BigDecimal("0.00"),
    val inventoryQuantity: Int? = 0
)