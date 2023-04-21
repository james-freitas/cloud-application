package com.cloud.sample.service

import com.cloud.sample.entity.Product
import com.cloud.sample.repository.ProductRepository
import org.springframework.stereotype.Service


@Service
class ProductService(val productRepository: ProductRepository) {
    fun findAll(): List<Product> = productRepository.findAll().map { Product(it.id, it.name!!, it.price!!) }
}