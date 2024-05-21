package com.dacs3.shop.ui.screens.product.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dacs3.shop.component.ErrorScreen
import com.dacs3.shop.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(private val productRepository: ProductRepository) : ViewModel() {
    private val _productDetailsUiState = MutableStateFlow(ProductDetailsUiState())
    val productDetailsUiState = _productDetailsUiState

    fun loadProduct(productId: String) {
        viewModelScope.launch {
            _productDetailsUiState.value = _productDetailsUiState.value.copy(errorMessage = null, isLoading = true)
            try {
                val id = productId.toInt()
                val response = productRepository.getProductById(id)
                if (response.isSuccessful) {
                    println("Product " + response.body()?.image)
                    _productDetailsUiState.value = _productDetailsUiState.value.copy(product = response.body(), isLoading = false)
                } else {
                    _productDetailsUiState.value = _productDetailsUiState.value.copy(errorMessage = response.errorBody().toString(), isLoading = false)
                }
            } catch (e: Exception) {
                _productDetailsUiState.value = _productDetailsUiState.value.copy(errorMessage = e.message, isLoading = false)
            }
        }
    }
}