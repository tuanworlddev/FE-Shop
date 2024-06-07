package com.dacs3.shop.model

data class CartItemDto(
    val variantId: Int,
    val quantity: Int,
    val price: Double
)
