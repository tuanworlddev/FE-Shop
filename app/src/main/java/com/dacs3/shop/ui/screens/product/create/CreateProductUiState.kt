package com.dacs3.shop.ui.screens.product.create

import androidx.compose.ui.graphics.ImageBitmap
import com.dacs3.shop.model.Category
import com.dacs3.shop.model.Color
import com.dacs3.shop.model.Size
import com.dacs3.shop.model.Variant

data class CreateProductUiState(
    val name: String = "",
    val description: String = "",
    val images: List<ImageBitmap> = emptyList(),
    val variants: List<Variant> = emptyList(),
    val colors: List<Color> = emptyList(),
    val sizes: List<Size> =  emptyList(),
    val categories: List<Category> = emptyList(),
    val categorySelected: Category? = null,
    val isLoaded: Boolean = false,
    val message: String? = null
)