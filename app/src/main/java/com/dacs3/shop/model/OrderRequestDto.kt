package com.dacs3.shop.model

data class OrderRequestDto(
    val addressId: Int,
    val total: Double,
    val orderItems: List<OrderItemRequestDto>
)
