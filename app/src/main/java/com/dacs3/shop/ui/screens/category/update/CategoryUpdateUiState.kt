package com.dacs3.shop.ui.screens.category.update

import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import com.dacs3.shop.model.Category

data class CategoryUpdateUiState(
    val imageUri: Uri? = null,
    val imageBitmap: ImageBitmap? = null,
    val name: String = "",
    val category: Category? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isActive: Boolean = false,
    val successMessage: String? = null
)
