package com.cloud.sample.service

import com.cloud.sample.entity.Product
import com.cloud.sample.repository.ProductEntity
import com.cloud.sample.repository.ProductRepository
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class ProductServiceTest {

    private val productRepository = mockk<ProductRepository>()
    private val productService = ProductService(productRepository)

    @BeforeEach
    fun setUp() {
        clearMocks(productRepository)
    }

    @Test
    fun `should find all the products`() {
        // given
        val product1 = ProductEntity( "Product 1", BigDecimal(10.0))
        val product2 = ProductEntity( "Product 2", BigDecimal(20.0))
        val productList = listOf(product1, product2)

        every { productRepository.findAll() } returns productList

        // when
        val result = productService.findAll()

        // then
        Assertions.assertThat(result).hasSize(2)

        Assertions.assertThat(result).containsExactly(
            Product(name = "Product 1", price = BigDecimal(10.0)),
            Product(name = "Product 2", price = BigDecimal(20.0))
        )
    }
}