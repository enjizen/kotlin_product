package com.tua.wanchalerm.example.product.controller

import com.tua.wanchalerm.example.product.model.entity.ProductEntity
import com.tua.wanchalerm.example.product.model.request.ProductRequest
import com.tua.wanchalerm.example.product.service.ProductService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/v1")
class ProductController(private val productService: ProductService) {

    @PostMapping("/products")
    fun create(@RequestBody request: ProductRequest,
               @RequestHeader("user-id") userId: String) : Mono<ProductEntity> {
        return productService.create(request)
    }

    @GetMapping("/products")
    fun get() : Flux<ProductEntity> {
        productService.getByName()
        return productService.getByName()
    }

    @GetMapping("/products/{id}")
    fun getById(@PathVariable("id") id: Int) : Mono<ProductEntity> {
        return productService.getById(id)
    }
}