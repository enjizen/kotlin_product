package com.tua.wanchalerm.example.product.service

import com.tua.wanchalerm.example.product.exception.DataNotFoundException
import com.tua.wanchalerm.example.product.model.entity.ProductEntity
import com.tua.wanchalerm.example.product.model.request.ProductRequest
import com.tua.wanchalerm.example.product.repository.ProductRepository
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import java.time.LocalDateTime
import java.util.UUID

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun getByName(): Flux<ProductEntity> {
        return productRepository.findAll()
    }

    fun getById(id: Int): Mono<ProductEntity> {
        return productRepository.findById(id)
            .switchIfEmpty {
                throw DataNotFoundException(message = "Bad request", description = "Product id $id not found")
            }
    }


    fun create(productRequest: ProductRequest): Mono<ProductEntity> {
        val productEntity = ProductEntity()
        BeanUtils.copyProperties(productRequest, productEntity)
        productEntity.code = UUID.randomUUID().toString()
        productEntity.createdDateTime = LocalDateTime.now()
        productEntity.updatedDateTime = LocalDateTime.now()
        productEntity.isDeleted = false
        return productRepository.save(productEntity)
    }

}