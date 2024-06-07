package com.dacs3.shop.ui.screens.product.search

import com.dacs3.shop.model.Product

data class SearchDetailsUiState(
    val products: List<Product> = emptyList(),
)