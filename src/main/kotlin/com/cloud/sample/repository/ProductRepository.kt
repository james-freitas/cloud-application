package com.cloud.sample.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import javax.persistence.*

@Repository
interface ProductRepository : JpaRepository<ProductEntity, Long>

@Entity
@Table(name = "product")
class ProductEntity {

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
