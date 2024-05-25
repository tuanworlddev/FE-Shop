package com.dacs3.shop.ui.screens.product.saleproduct

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dacs3.shop.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import com.dacs3.shop.ui.screens.product.saleproduct.ProductSaleUiState
import javax.inject.Inject

@HiltViewModel
class ProductSaleViewModel @Inject constructor(private val productRepository: ProductRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(ProductSaleUiState())
    val uiState = _uiState

    fun loadProduct() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val response = productRepository.getAllProducts()
                if (response.isSuccessful) {
                    val products = response.body()!!.filter { it.variants.first().sale > 0 }.reversed()
                    _uiState.value = _uiState.value.copy(products = products, isLoading = false)
                } else {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
            } catch (e: Exception) {
                Log.e("CALL API FAILED", e.message!!)
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}