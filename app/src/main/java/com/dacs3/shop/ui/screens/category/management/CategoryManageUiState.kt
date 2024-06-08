package com.dacs3.shop.ui.screens.category.management

import com.dacs3.shop.model.Category

data class CategoryManageUiState(
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
