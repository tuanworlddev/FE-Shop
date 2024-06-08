package com.dacs3.shop.ui.screens.category.update

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dacs3.shop.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryUpdateViewModel @Inject constructor(private val categoryRepository: CategoryRepository) : ViewModel() {
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
        _uiState.value = _uiState.value.copy(name = newName)
    }
}