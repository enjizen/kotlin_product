package com.tua.wanchalerm.example.product.service

import com.tua.wanchalerm.example.product.model.entity.ProductEntity
import com.tua.wanchalerm.example.product.model.request.ProductRequest
import com.tua.wanchalerm.example.product.repository.ProductRepository
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@ExtendWith(MockitoExtension::class)
class ProductServiceTest {

    @InjectMocks
    private val productService: ProductService? = null

    @Mock
    private val productRepository: ProductRepository? = null
    @Test
    fun `get all product`() {
        val productEntity = ProductEntity()
        productEntity.id = 1
        productEntity.code = "12344565645"
        productEntity.name = "test"
        val productEntityFlux = Flux.just(productEntity)
        Mockito.`when`(productRepository?.findAll()).thenReturn(productEntityFlux)
        val result = productService?.getAll()
        assertNotNull(result)
        assertEquals("12344565645", result!!.blockFirst()!!.code)
        Mockito.verify(productRepository, Mockito.times(1))!!.findAll()
    }

    @Test
    fun `get by Id`() {
        val productEntity = ProductEntity()
        productEntity.id = 1
        productEntity.code = "12344565645"
        productEntity.name = "test"
        Mockito.`when`(productRepository!!.findById(Mockito.anyInt())).thenReturn(Mono.just(productEntity))
        val result = productService?.getById(1)
        assertNotNull(result)
        assertEquals("12344565645", result!!.block()!!.code)
        Mockito.verify(productRepository, Mockito.times(1))!!.findById(Mockito.anyInt())
    }

    @Test
    fun `get by Id but id is null return empty`() {
        val result = productService?.getById(null)
        Mockito.verify(productRepository, Mockito.never())!!.findById(Mockito.anyInt())
    }

    @Test
    fun `get by name`() {
        val productEntity = ProductEntity()
        productEntity.id = 1
        productEntity.code = "12344565645"
        productEntity.name = "test"
        Mockito.`when`(productRepository!!.findByName(Mockito.anyString())).thenReturn(Mono.just(productEntity))
        val result = productService?.getAll("test")
        assertNotNull(result)
        assertEquals("12344565645", result!!.block()!!.code)
        Mockito.verify(productRepository, Mockito.times(1))!!.findByName(Mockito.anyString())
    }

    @Test
    fun create() {
        val productEntity = ProductEntity()
        productEntity.id = 1
        productEntity.code = "12344565645"
        productEntity.name = "test"

        val productRequest = ProductRequest(name = "test")

        Mockito.`when`(productRepository!!.save(Mockito.any())).thenReturn(Mono.just(productEntity))
        val result = productService!!.create(productRequest)
        assertNotNull(result)
        assertEquals("12344565645", result.block()!!.code)
        Mockito.verify(productRepository, Mockito.times(1))!!.save(Mockito.any())
    }
}