package com.dacs3.shop.ui.screens.category.details

import com.dacs3.shop.model.Category

data class CategoryDetailsUiState(
    val category: Category? = null,
    val products: List<Product> = listOf(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
