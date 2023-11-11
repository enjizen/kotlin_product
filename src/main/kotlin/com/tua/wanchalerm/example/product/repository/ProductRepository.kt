package com.tua.wanchalerm.example.product.repository

import com.tua.wanchalerm.example.product.model.entity.ProductEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface ProductRepository : ReactiveCrudRepository<ProductEntity, Int> {
    fun findByName(name: String): Mono<ProductEntity>
}