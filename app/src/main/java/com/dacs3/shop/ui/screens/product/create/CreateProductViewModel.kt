package com.dacs3.shop.ui.screens.product.create

import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dacs3.shop.model.Category
import com.dacs3.shop.model.Variant
import com.dacs3.shop.repository.CategoryRepository
import com.dacs3.shop.repository.ColorRepository
import com.dacs3.shop.repository.ProductRepository
import com.dacs3.shop.repository.SizeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val sizeRepository: SizeRepository,
    private val colorRepository: ColorRepository,
    private val categoryRepository: CategoryRepository
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
                _uiState.value = _uiState.value.copy(message = "Failed to load data: ${e.message}")
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
            Log.d("VARIANTS", variants.first().toString())
            _uiState.value = _uiState.value.copy(variants = variants)
        }
    }

    fun addImage(image: ImageBitmap) {
        val images = uiState.value.images.toMutableList()
        images.add(image)
        _uiState.value = _uiState.value.copy(images = images)
    }

    fun removeImage(index: Int) {
        val images = _uiState.value.images.toMutableList()
        if (index in images.indices) {
            images.removeAt(index)
            _uiState.value = _uiState.value.copy(images = images)
        }
    }
}