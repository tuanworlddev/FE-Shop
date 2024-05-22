package com.dacs3.shop.ui.screens.product.management

data class ProductManageUiState(
    val products: List<Product> = listOf(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
