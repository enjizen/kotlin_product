package com.tua.wanchalerm.example.product.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.math.BigDecimal
import java.text.DecimalFormat

class TwoDecimalPlacesBigDecimalSerializer : JsonSerializer<BigDecimal>() {
    override fun serialize(value: BigDecimal?, gen: JsonGenerator?, serializers: SerializerProvider?) {
        DecimalFormat("#0.00")
            .format(value?.setScale(2)?.toDouble())
            .run { gen?.writeString(this) }
    }
}