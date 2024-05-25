package com.dacs3.shop.ui.screens.product.saleproduct

import com.dacs3.shop.model.Product

data class ProductSaleUiState(
    val products: List<Product>? = null,
    val isLoading: Boolean = false
)
