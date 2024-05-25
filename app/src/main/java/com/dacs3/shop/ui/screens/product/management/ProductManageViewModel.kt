package com.dacs3.shop.ui.screens.product.management

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dacs3.shop.repository.ProductRepository
import com.dacs3.shop.repository.VariantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductManageViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val variantRepository: VariantRepository
) : ViewModel() {
    private val _productManageUiState = MutableStateFlow(ProductManageUiState())
    val productManageUiState = _productManageUiState

    fun loadProducts() {
        viewModelScope.launch {
            _productManageUiState.value = _productManageUiState.value.copy(errorMessage = null, isLoading = true)
            try {
                val response = productRepository.getAllProducts()
                if (response.isSuccessful) {
                    _productManageUiState.value = _productManageUiState.value.copy(products = response.body()!!, isLoading = false)
                } else {
                    _productManageUiState.value = _productManageUiState.value.copy(errorMessage = "Invalid Response", isLoading = false)
                }
            } catch (e: Exception) {
                _productManageUiState.value = _productManageUiState.value.copy(errorMessage = e.message, isLoading = false)
            }
        }
    }

    fun deleteProduct() {
        val currentVariantId = _productManageUiState.value.variantSelected
        viewModelScope.launch {
            try {
                val response = variantRepository.deleteVariant(currentVariantId!!)
                if (response.isSuccessful) {
                    loadProducts()
                }
            } catch (e: Exception) {
                Log.e("ERROR", "Calling api failed")
            }
        }
    }

    fun onSelectProductChange(id: Int) {
        _productManageUiState.value = _productManageUiState.value.copy(variantSelected = id)
    }
}