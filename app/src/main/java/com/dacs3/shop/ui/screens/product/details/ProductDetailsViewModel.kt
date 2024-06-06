package com.dacs3.shop.ui.screens.product.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dacs3.shop.component.ErrorScreen
import com.dacs3.shop.model.Color
import com.dacs3.shop.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
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
                    val result = response.body()
                    val firstVariant = result!!.variants.first()
                    _productDetailsUiState.value = _productDetailsUiState.value.copy(product = result, currentVariant = firstVariant, total = priceSale(firstVariant.price!!, firstVariant.sale!!, 1), isLoading = false)
                } else {
                    _productDetailsUiState.value = _productDetailsUiState.value.copy(errorMessage = response.errorBody().toString(), isLoading = false)
                }
            } catch (e: Exception) {
                _productDetailsUiState.value = _productDetailsUiState.value.copy(errorMessage = e.message, isLoading = false)
            }
        }
    }

    fun onSizeChange(sizeId: Int) {
        val currentVariants = _productDetailsUiState.value.product!!.variants
        val newVariant = currentVariants.find { it.size!!.id == sizeId}
        if (newVariant != null) {
            _productDetailsUiState.value = _productDetailsUiState.value.copy(currentVariant = newVariant, total = priceSale(newVariant.price!!, newVariant.sale!!, 1), quantity = 1)
        }
    }

    fun onColorChange(colorId: Int) {
        val currentVariants = _productDetailsUiState.value.product!!.variants
        val newVariant = currentVariants.find { it.size == _productDetailsUiState.value.currentVariant!!.size && it.color!!.id == colorId }
        if (newVariant != null) {
            _productDetailsUiState.value = _productDetailsUiState.value.copy(currentVariant = newVariant, total = priceSale(newVariant.price!!, newVariant.sale!!, 1), quantity = 1)
        }
    }

    fun getColorsForSize(sizeId: Int): List<Color?> {
        return _productDetailsUiState.value.product?.variants
            ?.filter { it.size!!.id == sizeId }
            ?.map { it.color }
            ?.distinctBy { it!!.id }
            ?: emptyList()
    }

    fun onReduce() {
        val currentQuantity = _productDetailsUiState.value.quantity
        val currentVariant = _productDetailsUiState.value.currentVariant
        if (currentQuantity > 1) {
            val newQuantity = currentQuantity - 1
            _productDetailsUiState.value = _productDetailsUiState.value.copy(quantity = newQuantity, total = priceSale(currentVariant!!.price!!, currentVariant.sale!!, newQuantity))
        }
    }

    fun onIncrease() {
        val currentQuantity = _productDetailsUiState.value.quantity
        val currentVariant = _productDetailsUiState.value.currentVariant
        if (currentQuantity < currentVariant!!.quantity!!) {
            val newQuantity = currentQuantity + 1
            _productDetailsUiState.value = _productDetailsUiState.value.copy(quantity = newQuantity, total = priceSale(currentVariant.price!!, currentVariant.sale!!, newQuantity))
        }
    }

    private fun priceSale(price: Double, sale: Double, quantity: Int): Double {
        return roundDouble(price * (1 - (sale / 100)) * quantity)
    }

    fun roundDouble(value: Double): Double {
        return BigDecimal(value).setScale(2, RoundingMode.HALF_EVEN).toDouble()
    }
}