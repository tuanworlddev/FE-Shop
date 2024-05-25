package com.dacs3.shop.ui.screens.product.create

import com.dacs3.shop.model.Variant

data class CreateProductUiState(
    val name: String = "",
    val description: String = "",
    val images: List<String> = emptyList(),
    val variants: List<Variant> = emptyList(),
    val isLoaded: Boolean = false,
    val message: String? = null
)