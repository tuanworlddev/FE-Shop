package com.dacs3.shop.model

data class OrderItem(
    val id: Int? = null,
    val productName: String? = null,
    val productImage: String? = null,
    val quantity: Int? = null,
    val variant: Variant? = null
)
