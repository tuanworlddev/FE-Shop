package com.dacs3.shop.ui.screens.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dacs3.shop.model.Cart
import com.dacs3.shop.model.UpdateCart
import com.dacs3.shop.repository.AuthRepository
import com.dacs3.shop.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState

    fun loadCart() {
        viewModelScope.launch {
            if (isUserExists()) {
                try {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                    val response = cartRepository.getCarts()
                    if (response.isSuccessful) {
                        _uiState.value = _uiState.value.copy(
                            carts = response.body() ?: emptyList(),
                            isLoading = false
                        )
                    } else {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                    }
                } catch (e: Exception) {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
            }
        }
    }

    fun deleteAllCart() {
        viewModelScope.launch {
            try {
                _uiState.value.carts.forEach {
                    cartRepository.deleteCart(it.id!!)
                }
                loadCart()
            } catch (e: Exception) {

            }
        }
    }

    private fun deleteCart(cartId: Int) {
        viewModelScope.launch {
            try {
                cartRepository.deleteCart(cartId)
                loadCart()
            } catch (e: Exception) {

            }
        }
    }

    fun isUserExists(): Boolean {
        return authRepository.user != null
    }

    fun increaseQuantity(cartId: Int) {
        val cart = _uiState.value.carts.find { it.id == cartId }
        if (cart != null) {
            val newQuantity = cart.quantity!! + 1
            _uiState.value = _uiState.value.copy(carts = _uiState.value.carts.map {
                if (it.id == cartId) {
                    it.copy(quantity = newQuantity)
                } else {
                    it
                }
            })
            onUpdateCart(cartId, newQuantity)
        }
    }

    fun reduceQuantity(cartId: Int) {
        val cart = _uiState.value.carts.find { it.id == cartId }
        if (cart != null) {
            if (cart.quantity!! > 1) {
                val newQuantity = cart.quantity - 1
                _uiState.value = _uiState.value.copy(carts = _uiState.value.carts.map {
                    if (it.id == cartId) {
                        it.copy(quantity = newQuantity)
                    } else {
                        it
                    }
                })
                onUpdateCart(cartId, newQuantity)
            } else {
                deleteCart(cartId)
            }
        }
    }


    private fun onUpdateCart(cartId: Int, quantity: Int) {
        viewModelScope.launch {
            try {
                cartRepository.updateCart(cartId, UpdateCart(quantity))
            } catch (e: Exception) {

            }
        }
    }


    fun roundDouble(value: Double): Double {
        return BigDecimal(value).setScale(2, RoundingMode.HALF_EVEN).toDouble()
    }

}