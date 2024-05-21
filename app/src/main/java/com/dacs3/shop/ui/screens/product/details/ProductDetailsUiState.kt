package com.dacs3.shop.ui.screens.product.details

import com.dacs3.shop.model.Product

data class ProductDetailsUiState(
    val product: Product? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)