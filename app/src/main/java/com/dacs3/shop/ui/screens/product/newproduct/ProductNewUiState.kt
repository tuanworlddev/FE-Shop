package com.dacs3.shop.ui.screens.product.newproduct

import com.dacs3.shop.model.Product

data class ProductNewUiState(
    val products: List<Product>? = null,
    val isLoading: Boolean = false
)
