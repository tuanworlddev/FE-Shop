package com.dacs3.shop.ui.screens.category.create

import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dacs3.shop.model.Category
import com.dacs3.shop.repository.CategoryRepository
import com.dacs3.shop.repository.UploadImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val uploadImageRepository: UploadImageRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(CreateCategoryUiState())
    val uiState = _uiState

    fun onNameChanged(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun onDescriptionChanged(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }

    fun onImageChanged(image: Uri) {
        _uiState.value = _uiState.value.copy(image = image)
    }

    fun onImageBitmapChanged(imageBitmap: ImageBitmap) {
        _uiState.value = _uiState.value.copy(imageBitmap = imageBitmap)
    }

    fun onCreateCategory() {
        viewModelScope.launch {
            val name = _uiState.value.name
            val description = _uiState.value.description
            val image = _uiState.value.image

            if (name.isNotEmpty() && image != Uri.EMPTY) {
                _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null, successMessage = null)
                try {
                    val imageUrl = uploadImageRepository.uploadImage(image)
                    val response = categoryRepository.addCategory(Category(name = name, description = description, image = imageUrl))
                    if (response.isSuccessful) {
                        _uiState.value = _uiState.value.copy(isLoading = false, successMessage = "Category created successfully", name = "", description = "", image = Uri.EMPTY, imageBitmap = null)
                    } else {
                        _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = "Failed to create category")
                    }
                } catch (e: Exception) {
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = "Failed to create category")
                }
            }
        }
    }
}