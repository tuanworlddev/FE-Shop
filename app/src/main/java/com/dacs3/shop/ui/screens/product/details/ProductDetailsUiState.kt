package com.dacs3.shop.ui.screens.product.details

import com.dacs3.shop.model.Product
import com.dacs3.shop.model.Variant

data class ProductDetailsUiState(
    val product: Product? = null,
    val currentVariant: Variant? = null,
    val quantity: Int = 1,
    val total: Double = 0.0,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)