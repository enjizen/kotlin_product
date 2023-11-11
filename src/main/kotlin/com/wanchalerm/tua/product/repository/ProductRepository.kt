package com.wanchalerm.tua.product.repository

import com.wanchalerm.tua.product.model.ProductEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface ProductRepository : ReactiveCrudRepository<ProductEntity, Int> {
    fun findByName(name: String): Mono<ProductEntity>
}