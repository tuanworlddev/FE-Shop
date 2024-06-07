package com.dacs3.shop.model

data class CartItem(
    val id: Int,
    val product: Product,
    val variant: Variant,
    val price: Int,
    val quantity: Int
)
