package com.dacs3.shop.model

data class CartDto(
    val userId: Int,
    val total: Double,
    val cartItems: List<CartItemDto>
)
