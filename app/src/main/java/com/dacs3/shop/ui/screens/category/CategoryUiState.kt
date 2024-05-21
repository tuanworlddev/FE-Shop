package com.dacs3.shop.ui.screens.category

import com.dacs3.shop.model.Category

data class CategoryUiState(
    val categories: List<Category> = listOf(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)