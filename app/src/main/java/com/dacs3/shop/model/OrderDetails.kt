package com.dacs3.shop.model

data class OrderDetails(
    val id: Int? = null,
    val total: Double? = null,
    val status: OrderStatus? = null,
    val items: List<OrderItem>? = null
)
