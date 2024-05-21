package com.dacs3.shop.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dacs3.shop.repository.CategoryRepository
import com.dacs3.shop.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    fun loadData() {
        viewModelScope.launch {
            _homeUiState.value = _homeUiState.value.copy(isLoading = true)
            try {
                val categoriesDeferred = async { categoryRepository.getAllCategories() }
                val productsDeferred = async { productRepository.getAllProducts() }

                val categoriesResponse = categoriesDeferred.await()
                val productsResponse = productsDeferred.await()

                if (categoriesResponse.isSuccessful && productsResponse.isSuccessful) {
                    val products = productsResponse.body()!!
                    val (saleProducts, newProducts) = products.partition { it.sale!! > 0 }

                    _homeUiState.value = HomeUiState(
                        saleProducts = saleProducts,
                        newProducts = newProducts,
                        categories = categoriesResponse.body()!!,
                        isLoading = false
                    )
                } else {
                    _homeUiState.value = _homeUiState.value.copy(isLoading = false)
                    Log.e("ERROR", "Failed to fetch data: ${categoriesResponse.errorBody()} and ${productsResponse.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("ERROR", "Exception occurred: ${e.message}")
                _homeUiState.value = _homeUiState.value.copy(isLoading = false)
            }
        }
    }
}
