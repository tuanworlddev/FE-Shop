package com.dacs3.shop.model

data class Cart(
    val id: Int,
    val total: Double,
    val cartItems: List<CartItem>
)
