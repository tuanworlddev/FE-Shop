package com.dacs3.shop.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dacs3.shop.model.Category
import com.dacs3.shop.model.Product
import com.dacs3.shop.repository.AuthRepository
import com.dacs3.shop.repository.CategoryRepository
import com.dacs3.shop.repository.DataStoreRepository
import com.dacs3.shop.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Response
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    fun loadData() {
        viewModelScope.launch {
            _homeUiState.value = _homeUiState.value.copy(isLoading = true)

            val categoriesDeferred = async { categoryRepository.getAllCategories() }
            val productsDeferred = async { productRepository.getAllProducts() }

            try {
                val categoriesResponse = categoriesDeferred.await()
                val productsResponse = productsDeferred.await()

                if (categoriesResponse.isSuccessful && productsResponse.isSuccessful) {
                    val products = productsResponse.body()!!.reversed()
                    val (saleProducts, newProducts) = products.partition {
                        (it.variants.firstOrNull()?.sale ?: 0.0) > 0.0
                    }

                    _homeUiState.value = _homeUiState.value.copy(
                        saleProducts = saleProducts,
                        newProducts = newProducts,
                        categories = categoriesResponse.body() ?: emptyList(),
                        isLoading = false
                    )
                } else {
                    handleErrors(categoriesResponse, productsResponse)
                }
            } catch (e: Exception) {
                Log.e("ERROR", "Exception occurred: ${e.message}")
                _homeUiState.value = _homeUiState.value.copy(isLoading = false)
            }
        }
    }

    fun loadUser() {
        viewModelScope.launch {
            if (dataStoreRepository.token.first() != null && authRepository.user == null) {
                try {
                    val result = authRepository.getUser()
                    if (result.isSuccessful) {
                        val user = result.body()
                        if (user != null) {
                            authRepository.user = user
                        }
                    }
                } catch (e: Exception) {
                    Log.e("ERROR", "Exception occurred: ${e.message}")
                }
            }
        }
    }

    private fun handleErrors(categoriesResponse: Response<List<Category>>, productsResponse: Response<List<Product>>) {
        val categoryError = categoriesResponse.errorBody()?.string()
        val productError = productsResponse.errorBody()?.string()
        Log.e("ERROR", "Failed to fetch data: $categoryError and $productError")

        _homeUiState.value = _homeUiState.value.copy(isLoading = false)
    }
}
