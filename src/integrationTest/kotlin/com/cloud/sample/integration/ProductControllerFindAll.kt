package com.cloud.sample.integration

import com.cloud.sample.controller.ProductResponse
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.assertThat
import org.flywaydb.core.Flyway
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.math.BigDecimal

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = [ProductControllerIntegrationTest.Initializer::class])
@ExtendWith(SpringExtension::class)
@ActiveProfiles("test")
class ProductControllerIntegrationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    internal class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
            postgresContainer.start()

            TestPropertyValues.of(
                "spring.datasource.url=${postgresContainer.jdbcUrl}",
                "spring.datasource.username=${postgresContainer.username}",
                "spring.datasource.password=${postgresContainer.password}"
            ).applyTo(configurableApplicationContext.environment)
        }
    }

    companion object {

        @Container
        val postgresContainer = PostgreSQLContainer<Nothing>("postgres:12").apply {
            withDatabaseName("test")
            withUsername("test")
            withPassword("test")
            withExposedPorts(5440, 5432)
        }

        @BeforeAll
        @JvmStatic
        fun setupFlyway() {
            val flyway = Flyway.configure()
                .dataSource(postgresContainer.jdbcUrl, "test", "test")
                .locations("classpath:/db/migration")
                .load()
            flyway.migrate()
        }

        @AfterAll
        @JvmStatic
        fun cleanupFlyway() {
            val flyway = Flyway.configure()
                .dataSource(postgresContainer.jdbcUrl, "test", "test")
                .locations("classpath:/db/migration")
                .load()
            flyway.clean()
        }
    }

    @Test
    fun `should return a list with all products`() {
        val response = mockMvc.get("/products") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
        }.andReturn().response

        val expected = listOf(
            ProductResponse("Soccer ball", BigDecimal("100.22")),
            ProductResponse("Basket ball", BigDecimal("11.35")),
            ProductResponse("Volley ball", BigDecimal("1.99"))
        )

        val objectMapper = ObjectMapper()
        val actual = objectMapper.readValue<List<ProductResponse>>(response.contentAsString)
        assertThat(actual).containsExactlyElementsOf(expected)
    }
}