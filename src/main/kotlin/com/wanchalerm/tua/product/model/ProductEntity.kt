package com.wanchalerm.tua.product.model

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.sql.Timestamp

@Table(name = "products")
data class ProductEntity(
    @Id
    @Column("id")
    val id: Int? = null,

    @Column("name")
    val name: String? = null,

    @Column("price")
    val price: BigDecimal? = BigDecimal("0.00"),

    @Column("inventory_quantity")
    val inventoryQuantity: Int? = 0,

    @Column("created_date_time")
    @CreatedBy
    val createdDateTime: Timestamp? = null,

    @Column("updated_date_time")
    @LastModifiedDate
    val updatedDateTime: Timestamp? = null,

    @Column("is_deleted")
    val isDeleted: Boolean? = false
)
