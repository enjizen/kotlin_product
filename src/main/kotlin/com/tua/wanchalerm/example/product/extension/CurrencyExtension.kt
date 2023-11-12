package com.tua.wanchalerm.example.product.extension

import java.math.BigDecimal




//CurrencyExtension

private val BATH_SATANG_VALUE = BigDecimal(100)

fun BigDecimal.convertBathToSaTang(): BigDecimal {
    return this.multiply(BATH_SATANG_VALUE)
}

fun BigDecimal.convertSaTangToBath(): BigDecimal {
    return this.divide(BATH_SATANG_VALUE)
}
