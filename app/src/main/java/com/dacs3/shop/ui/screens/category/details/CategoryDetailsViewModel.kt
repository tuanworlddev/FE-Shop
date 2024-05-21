package com.dacs3.shop.ui.screens.category.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dacs3.shop.repository.CategoryRepository
import com.dacs3.shop.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryDetailsViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _categoryDetailsUiState = MutableStateFlow(CategoryDetailsUiState())
    val categoryDetailsUiState = _categoryDetailsUiState

    fun loadData(categoryId: String) {
        viewModelScope.launch {
            _categoryDetailsUiState.value = _categoryDetailsUiState.value.copy(isLoading = true, errorMessage = null)
            try {
                val id = categoryId.toInt()
                val categoryDeferred = async { categoryRepository.getCategoryById(id) }
                val productDeferred = async { productRepository.getProductByCategory(id) }

                val responseCategory = categoryDeferred.await()
                val responseProduct = productDeferred.await()

                if (responseCategory.isSuccessful && responseProduct.isSuccessful) {
                    _categoryDetailsUiState.value = _categoryDetailsUiState.value.copy(category = responseCategory.body()!!, products = responseProduct.body()!!, isLoading = false)
                } else {
                    _categoryDetailsUiState.value = _categoryDetailsUiState.value.copy(isLoading = false)
                }
            } catch (e: Exception) {
                _categoryDetailsUiState.value = _categoryDetailsUiState.value.copy(isLoading = false, errorMessage = e.message)
            }
        }
    }
}