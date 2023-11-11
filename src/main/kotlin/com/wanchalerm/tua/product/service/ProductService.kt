package com.wanchalerm.tua.product.service

import com.wanchalerm.tua.product.model.ProductEntity
import com.wanchalerm.tua.product.model.request.ProductRequest
import com.wanchalerm.tua.product.repository.ProductRepository
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

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


    fun create(productRequest: ProductRequest): Mono<ProductEntity> {
            val productEntity = ProductEntity()
            BeanUtils.copyProperties(productRequest, productEntity)
            return productRepository.save(productEntity)
    }

}