package com.dacs3.shop.ui.screens.category.management

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dacs3.shop.model.Category
import com.dacs3.shop.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryManageViewModel @Inject constructor(private val categoryRepository: CategoryRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(CategoryManageUiState())
    val uiState = _uiState

    fun loadCategories() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                val response = categoryRepository.getAllCategories()
                if (response.isSuccessful) {
                    val categories = response.body()!!
                    _uiState.value = _uiState.value.copy(categories = categories, isLoading = false)
                } else {
                    _uiState.value = _uiState.value.copy(error = "Failed to load categories", isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = "Failed to load categories", isLoading = false)
            }
        }
    }
}