package com.dacs3.shop.model

data class Cart(
    val id: Int? = null,
    val productName: String? = null,
    val productImage: String? = null,
    val variant: Variant? = null,
    val quantity: Int? = null,
)
