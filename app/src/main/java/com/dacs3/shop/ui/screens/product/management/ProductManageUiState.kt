package com.dacs3.shop.ui.screens.product.management

import com.dacs3.shop.model.Product

data class ProductManageUiState(
    val products: List<Product>? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
