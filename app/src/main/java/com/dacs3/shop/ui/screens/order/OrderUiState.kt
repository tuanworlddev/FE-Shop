package com.dacs3.shop.ui.screens.order

import com.dacs3.shop.model.OrderDetails
import com.dacs3.shop.model.OrderStatus

data class OrderUiState(
    val orders: List<OrderDetails> = emptyList(),
    val orderFilter: List<OrderDetails> = emptyList(),
    val listStatus: Set<OrderStatus> = setOf(
        OrderStatus.Pending,
        OrderStatus.Confirmed,
        OrderStatus.Shipped,
        OrderStatus.Delivered,
        OrderStatus.Cancelled,
        OrderStatus.Returned
    ),
    val selectedStatus: OrderStatus = listStatus.first(),
    val loading: Boolean = false
)
