package com.dacs3.shop.ui.screens.category.update

import android.net.Uri
import com.dacs3.shop.model.Category

data class CategoryUpdateUiState(
    val imageUri: Uri? = null,
    val name: String = "",
    val category: Category? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isActive: Boolean = false
)
