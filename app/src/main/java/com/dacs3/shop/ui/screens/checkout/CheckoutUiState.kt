package com.dacs3.shop.ui.screens.checkout

import com.dacs3.shop.model.Address
import com.dacs3.shop.model.Cart

data class CheckoutUiState(
    val carts: List<Cart> = emptyList(),
    val addresses: List<Address> = emptyList(),
    val selectedAddress: Address? = null,
    val subtotal: Double = 0.0,
    val tax: Double = 0.0,
    val total: Double = 0.0,
    val shippingCost: Double = 3.0,
    val loading: Boolean = false,
    val error: String? = null,
)