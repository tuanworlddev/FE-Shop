package com.dacs3.shop.ui.screens.product.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dacs3.shop.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchDetailsViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchDetailsUiState())
    val uiState = _uiState

    fun loadProducts(query: String) {
        viewModelScope.launch {
            try {
                val response = productRepository.searchProduct(query)
                if (response.isSuccessful) {
                    val products = response.body()
                    _uiState.value = SearchDetailsUiState(products = products!!)
                }
            } catch (e: Exception) {

            }
        }
    }

}