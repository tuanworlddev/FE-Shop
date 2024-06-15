package com.dacs3.shop.ui.screens.cart

import com.dacs3.shop.model.Cart

data class CartUiState(
    val carts: List<Cart> = emptyList(),
    val selectedItems: Set<Int?> = emptySet(),
    val totalPrice: Double = 0.0,
    val isLoading: Boolean = false,
)
