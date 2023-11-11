package com.tua.wanchalerm.example.product

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableDiscoveryClient
@SpringBootApplication
class KotlinProductApplication

fun main(args: Array<String>) {
	runApplication<KotlinProductApplication>(*args)
}
