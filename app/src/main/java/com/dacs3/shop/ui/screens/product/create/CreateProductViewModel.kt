package com.dacs3.shop.ui.screens.product.create

import androidx.lifecycle.ViewModel
import com.dacs3.shop.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CreateProductViewModel @Inject constructor(private val productRepository: ProductRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(CreateProductUiState())
    val uiState = _uiState

    fun onNameChanged(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun onDescriptionChanged(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }

}