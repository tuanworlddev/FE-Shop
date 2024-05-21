package com.dacs3.shop.ui.screens.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dacs3.shop.model.ResponseDto
import com.dacs3.shop.repository.CategoryRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val categoryRepository: CategoryRepository) : ViewModel() {
    private val _categoryUiState = MutableStateFlow(CategoryUiState())
    val categoryUiState: StateFlow<CategoryUiState> = _categoryUiState

    fun loadCategories() {
        viewModelScope.launch {
            try {
                _categoryUiState.value = _categoryUiState.value.copy(errorMessage = null, isLoading = true)
                val response = categoryRepository.getAllCategories()
                if (response.isSuccessful) {
                    _categoryUiState.value = _categoryUiState.value.copy(categories = response.body()!!, isLoading = false)
                } else {
                    val gson = Gson()
                    val errorJson = response.errorBody()?.string()
                    val errorResponse = gson.fromJson(errorJson, ResponseDto::class.java)
                    _categoryUiState.value = _categoryUiState.value.copy(errorMessage = errorResponse.message, isLoading = false)
                }
            } catch (e: Exception) {
                _categoryUiState.value = _categoryUiState.value.copy(errorMessage = e.message, isLoading = false)
            }
        }
    }
}