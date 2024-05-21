package com.dacs3.shop.model

import java.math.BigDecimal

data class Product(
    val id: Int? = null,
    val attributes: List<Attribute>? = null,
    val category: String? = null,
    val createdAt: String? = null,
    val description: String? = null,
    val image: String? = null,
    val name: String? = null,
    val price: BigDecimal? = null,
    val quantity: Int? = null,
    val sale: Float? = null,
    val updatedAt: String? = null
) {
    override fun toString(): String {
        return "Product(id=$id, category=$category, createdAt=$createdAt, description=$description, image=$image, name=$name, price=$price, quantity=$quantity, sale=$sale, updatedAt=$updatedAt)"
    }
}