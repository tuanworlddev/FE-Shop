package com.dacs3.shop.ui.screens.home

import com.dacs3.shop.model.Category
import com.dacs3.shop.model.Product
import com.dacs3.shop.model.User

data class HomeUiState(
    val user: User? = null,
    val categories: List<Category>? = null,
    val newProducts: List<Product>? = null,
    val saleProducts: List<Product>? = null,
    val cartCount: Int = 0,
    val isLoading: Boolean = false
)
