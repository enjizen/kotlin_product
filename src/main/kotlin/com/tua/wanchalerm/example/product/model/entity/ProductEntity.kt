package com.tua.wanchalerm.example.product.model.entity

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDateTime

@Table(name = "products")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
open class ProductEntity  {
   @Id
   open var id: Int? = null

   @Column("code")
   open var code: String? = null

   @Column("name")
   open var name: String? = null

   @Column("description")
   open var description: String? = null

   @Column("price")
   open var price: BigDecimal? = BigDecimal("0.00")

   @Column("inventory_quantity")
   open var inventoryQuantity: Int? = 0

   @Column("created_date_time")
   @CreatedDate
   open var createdDateTime: LocalDateTime? = null

   @Column("updated_date_time")
   @LastModifiedDate
   open var updatedDateTime: LocalDateTime? = null

   @Column("is_deleted")
   @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
   open var isDeleted: Boolean? = false
}
