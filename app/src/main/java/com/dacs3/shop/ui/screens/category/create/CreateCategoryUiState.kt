package com.dacs3.shop.ui.screens.category.create

import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap

data class CreateCategoryUiState(
    val name: String = "",
    val description: String = "",
    val image: Uri = Uri.EMPTY,
    val imageBitmap: ImageBitmap? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)
