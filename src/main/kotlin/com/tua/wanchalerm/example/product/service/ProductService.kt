package com.tua.wanchalerm.example.product.service

import com.tua.wanchalerm.example.product.exception.DataNotFoundException
import com.tua.wanchalerm.example.product.model.entity.ProductEntity
import com.tua.wanchalerm.example.product.model.request.ProductRequest
import com.tua.wanchalerm.example.product.repository.ProductRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import java.time.LocalDateTime
import java.util.*

@Service
class ProductService(private val productRepository: ProductRepository) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun getAll(): Flux<ProductEntity> {
        return productRepository.findAll()
    }

    fun getById(id: Int): Mono<ProductEntity> {
        logger.info("Get By id {}", id)
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