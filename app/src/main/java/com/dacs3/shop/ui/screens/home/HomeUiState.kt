package com.dacs3.shop.ui.screens.home

import com.dacs3.shop.model.Category
import com.dacs3.shop.model.Product

data class HomeUiState(
    val categories: List<Category> = listOf(),
    val newProducts: List<Product> = listOf(),
    val saleProducts: List<Product> = listOf(),
    val isLoading: Boolean = false
)
