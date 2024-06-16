package com.dacs3.shop.ui.screens.order.details

import com.dacs3.shop.model.OrderDetails

data class OrderDetailsUiState(
    val orderDetails: OrderDetails? = null,
    val isViewAll: Boolean = false,
    val loading: Boolean = false,
    val isCancelled: Boolean = false
)
