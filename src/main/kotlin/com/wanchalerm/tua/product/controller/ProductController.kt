package com.wanchalerm.tua.product.controller

import com.wanchalerm.tua.product.model.ProductEntity
import com.wanchalerm.tua.product.model.request.ProductRequest
import com.wanchalerm.tua.product.service.ProductService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/v1")
class ProductController(private val productService: ProductService) {

    @PostMapping("products")
    fun create(@RequestBody request: ProductRequest) : Mono<ProductEntity> {
        return productService.create(request)
    }
}