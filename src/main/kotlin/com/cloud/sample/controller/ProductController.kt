package com.cloud.sample.controller

import com.cloud.sample.service.ProductService
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@RequestMapping
class ProductController(private val productService: ProductService) {

    @GetMapping("/products")
    fun list(): List<ProductResponse> {
        return productService.findAll().map { ProductResponse(it.name, it.price) }
    }
}

data class ProductResponse(
    @JsonProperty("name")
    val name: String,
    @JsonProperty("price")
    val price: BigDecimal
)