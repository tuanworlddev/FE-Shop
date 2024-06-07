package com.dacs3.shop.ui.screens.product.create

import android.net.Uri
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dacs3.shop.model.Category
import com.dacs3.shop.model.ProductDto
import com.dacs3.shop.model.Variant
import com.dacs3.shop.model.VariantDto
import com.dacs3.shop.repository.CategoryRepository
import com.dacs3.shop.repository.ColorRepository
import com.dacs3.shop.repository.ProductRepository
import com.dacs3.shop.repository.SizeRepository
import com.dacs3.shop.repository.UploadImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val sizeRepository: SizeRepository,
    private val colorRepository: ColorRepository,
    private val categoryRepository: CategoryRepository,
    private val uploadImageRepository: UploadImageRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreateProductUiState())
    val uiState = _uiState

    fun loadData() {
        viewModelScope.launch {
            try {
                val sizes = sizeRepository.getAllSizes().body() ?: emptyList()
                val colors = colorRepository.getAllColors().body() ?: emptyList()
                val categories = categoryRepository.getAllCategories().body() ?: emptyList()
                _uiState.value = _uiState.value.copy(sizes = sizes, colors = colors, categories = categories)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = "Failed to load data: ${e.message}")
            }
        }
    }

    fun onNameChanged(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun onDescriptionChanged(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }

    fun onCategoryChanged(category: Category) {
        _uiState.value = _uiState.value.copy(categorySelected = category)
    }

    fun addVariant() {
        val variants = _uiState.value.variants.toMutableList()
        variants.add(Variant(price = 0.0, quantity = 0, sale = 0.0, size = _uiState.value.sizes.first(), color = _uiState.value.colors.first()))
        _uiState.value = _uiState.value.copy(variants = variants)
    }

    fun updateVariant(index: Int, variant: Variant) {
        val variants = _uiState.value.variants.toMutableList()
        if (index in variants.indices) {
            variants[index] = variant
            _uiState.value = _uiState.value.copy(variants = variants)
        }
    }

    fun addImage(image: ImageBitmap) {
        val images = uiState.value.images.toMutableList()
        images.add(image)
        _uiState.value = _uiState.value.copy(images = images)
    }

    fun addImageUri(imageUri: Uri) {
        val images = _uiState.value.imageUris.toMutableList()
        images.add(imageUri)
        _uiState.value = _uiState.value.copy(imageUris = images)
    }

    fun removeImage(index: Int) {
        val images = _uiState.value.images.toMutableList()
        val imageUris = _uiState.value.imageUris.toMutableList()
        if (index in images.indices) {
            images.removeAt(index)
            imageUris.removeAt(index)
            _uiState.value = _uiState.value.copy(images = images, imageUris = imageUris)
        }
    }

    fun createProduct() {
        val currentUiState = _uiState.value
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null, createProductSuccess = false)
                var hasError = false

                if (currentUiState.name.isBlank()) {
                    hasError = true
                }

                if (currentUiState.categorySelected == null) {
                    hasError = true
                }

                if (currentUiState.variants.isEmpty()) {
                    hasError = true
                }

                if (currentUiState.images.isEmpty()) {
                    hasError = true
                }

                if (currentUiState.variants.isEmpty()) {
                    hasError = true
                }

                if (!hasError) {
                    val imageUrls = currentUiState.imageUris.map { uploadImageRepository.uploadImage(it) }
                    val productDto = ProductDto(
                        name = currentUiState.name,
                        description = currentUiState.description,
                        categoryId = currentUiState.categorySelected?.id!!,
                        variants = currentUiState.variants.map { VariantDto(price = it.price!!, quantity = it.quantity!!, sale = it.sale!!, sizeId = it.size?.id!!, colorId = it.color?.id!!) },
                        images = imageUrls
                    )
                    productRepository.addProduct(productDto)
                    _uiState.value = _uiState.value.copy(isLoading = false, createProductSuccess = true)
                } else {
                    _uiState.value = _uiState.value.copy(errorMessage = "Please fill in all required fields", isLoading = false)
                }

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = "Failed to create product: ${e.message}", isLoading = false)
            }
        }
    }

    fun onErrorMessageChanged(errorMessage: String? = null) {
        _uiState.value = _uiState.value.copy(errorMessage = errorMessage)
    }

    fun onCreateProductSuccessChanged(createProductSuccess: Boolean = false) {
        _uiState.value = _uiState.value.copy(createProductSuccess = createProductSuccess)
    }

    fun resetUiState() {
        val currentUiState = _uiState.value
        _uiState.value = _uiState.value.copy(
            name = "",
            description = "",
            categorySelected = null,
            variants = emptyList(),
            images = emptyList(),
            categories = currentUiState.categories,
            colors = currentUiState.colors,
            sizes = currentUiState.sizes
        )
    }
}