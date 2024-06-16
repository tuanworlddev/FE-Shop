package com.dacs3.shop.ui.screens.order.item

import com.dacs3.shop.model.OrderDetails
import com.dacs3.shop.model.OrderStatus

data class OrderItemUiState(
    val orderDetails: OrderDetails? = null,
    val isViewAll: Boolean = false,
    val loading: Boolean = false,
    val isCancelled: Boolean = false,
    val listStatus: List<OrderStatus> = listOf(
        OrderStatus.Pending,
        OrderStatus.Confirmed,
        OrderStatus.Shipped,
        OrderStatus.Delivered,
        OrderStatus.Cancelled,
        OrderStatus.Returned
    ),
    val selectedStatus: OrderStatus? = null
)
