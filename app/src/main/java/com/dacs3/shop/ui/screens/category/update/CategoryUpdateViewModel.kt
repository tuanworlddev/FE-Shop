package com.dacs3.shop.ui.screens.category.update

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
class CategoryUpdateViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val uploadImageRepository: UploadImageRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CategoryUpdateUiState())
    val uiState = _uiState

    fun loadCategory(categoryId: Int) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                val response = categoryRepository.getCategoryById(categoryId)
                if (response.isSuccessful) {
                    val category = response.body()!!
                    _uiState.value = _uiState.value.copy(category = category, name = category.name!!, isLoading = false)
                } else {
                    _uiState.value = _uiState.value.copy(error = "Failed to load category", isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = "Failed to load category", isLoading = false)
            }
        }
    }

    fun onNameChanged(newName: String) {
        _uiState.value = _uiState.value.copy(isActive = if (!_uiState.value.isActive) newName != _uiState.value.category!!.name else true, name = newName)
    }

    fun onImageUriChanged(uri: Uri) {
        _uiState.value = _uiState.value.copy(isActive = true, imageUri = uri)
    }

    fun onImageBitmapChanged(imageBitmap: ImageBitmap) {
        _uiState.value = _uiState.value.copy(imageBitmap = imageBitmap)
    }

    fun onUpdateCategory() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null, successMessage = null)
            try {
                val currentUiState = _uiState.value
                var imageUrl = currentUiState.category?.image!!
                if (currentUiState.imageUri != null) {
                    imageUrl = uploadImageRepository.uploadImage(currentUiState.imageUri)
                }
                val category = Category(
                    name = currentUiState.name,
                    image = imageUrl,
                    description = null
                )
                val response = categoryRepository.updateCategory(currentUiState.category.id!!, category = category)
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(isLoading = false, successMessage = "Category updated successfully")
                } else {
                    _uiState.value = _uiState.value.copy(error = "Failed to update category", isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = "Failed to update category")
            }

        }
    }
}