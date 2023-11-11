package com.tua.wanchalerm.example.product.service

import com.tua.wanchalerm.example.product.model.entity.ProductEntity
import com.tua.wanchalerm.example.product.model.request.ProductRequest
import com.tua.wanchalerm.example.product.repository.ProductRepository
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.util.UUID

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun get(): Flux<ProductEntity> {
        return productRepository.findAll()
    }

    fun get(name: String?): Mono<ProductEntity> {
        name?.let {
            return productRepository.findByName(it)
        } ?: run {
            return Mono.empty()
        }
    }

    fun getById(id: Int?): Mono<ProductEntity> {
        id?.let {
            return productRepository.findById(id)
        } ?: run {
            return Mono.empty()
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