package com.cloud.sample.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Repository
interface ProductRepository : JpaRepository<Product, Long>

@Entity
class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
    var name: String? = null
    var price: BigDecimal? = null

    constructor(name: String, price: BigDecimal) {
        this.name = name
        this.price = price
    }
}
