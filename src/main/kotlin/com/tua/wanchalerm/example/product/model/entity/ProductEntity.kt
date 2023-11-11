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
 class ProductEntity : Serializable {
   @Id
   var id: Int? = null

   @Column("code")
   var code: String? = null

   @Column("name")
   var name: String? = null

   @Column("description")
   var description: String? = null

   @Column("price")
   var price: BigDecimal? = BigDecimal("0.00")

   @Column("inventory_quantity")
   var inventoryQuantity: Int? = 0

   @Column("created_date_time")
   @CreatedDate
   var createdDateTime: LocalDateTime? = null

   @Column("updated_date_time")
   @LastModifiedDate
   var updatedDateTime: LocalDateTime? = null

   @Column("is_deleted")
   @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
   var isDeleted: Boolean? = false
}
