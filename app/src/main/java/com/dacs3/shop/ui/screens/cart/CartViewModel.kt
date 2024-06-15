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
                        _uiState.value = _uiState.value.copy(carts = response.body() ?: emptyList(), isLoading = false)
                    } else {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                    }
                } catch (e: Exception) {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
            }
        }
    }

    fun isUserExists(): Boolean {
        return authRepository.user != null
    }

    fun selectAllItems() {
        val selectedAll = _uiState.value.carts.map { it.id }.toSet()
        _uiState.value = _uiState.value.copy(selectedItems = selectedAll, totalPrice = loadTotalPrice(selectedAll))
    }

    fun deselectAllItems() {
        _uiState.value = _uiState.value.copy(selectedItems = emptySet(), totalPrice = loadTotalPrice(
            emptySet()
        ))
    }

    fun toggleSelectItem(itemId: Int) {
        val currentSelectedItems = _uiState.value.selectedItems.toMutableSet()
        if (currentSelectedItems.contains(itemId)) {
            currentSelectedItems.remove(itemId)
        } else {
            currentSelectedItems.add(itemId)
        }
        _uiState.value = _uiState.value.copy(selectedItems = currentSelectedItems.toSet(), totalPrice = loadTotalPrice(currentSelectedItems.toSet()))
    }

    private fun onUpdateCart(cartId: Int, quantity: Int) {
        viewModelScope.launch {
            try {
                cartRepository.updateCart(cartId, UpdateCart(quantity))
            } catch (e: Exception) {

            }
        }
    }

    fun onReduce(cartId: Int) {
        val updatedCarts = _uiState.value.carts.map { cart ->
            if (cart.id == cartId && cart.quantity!! > 1) {
                cart.copy(quantity = cart.quantity - 1)
            } else {
                cart
            }
        }
        _uiState.value = _uiState.value.copy(carts = updatedCarts, totalPrice = loadTotalPrice(_uiState.value.selectedItems))
        val updatedCart = updatedCarts.find { it.id == cartId }
        updatedCart?.let {
            onUpdateCart(cartId, it.quantity!!)
        }
    }

    fun onIncrease(cartId: Int) {
        val updatedCarts = _uiState.value.carts.map { cart ->
            if (cart.id == cartId) {
                cart.copy(quantity = cart.quantity!! + 1)
            } else {
                cart
            }
        }
        _uiState.value = _uiState.value.copy(carts = updatedCarts, totalPrice = loadTotalPrice(_uiState.value.selectedItems))
        val updatedCart = updatedCarts.find { it.id == cartId }
        updatedCart?.let {
            onUpdateCart(cartId, it.quantity!!)
        }
    }

    fun onDeleteCart(cartId: Int) {
        viewModelScope.launch {
            try {
                cartRepository.deleteCart(cartId)
                loadCart()
            } catch (e: Exception) {

            }
        }
    }

    private fun loadTotalPrice(currentSelectedItems: Set<Int?>): Double {
        val totalPrice = currentSelectedItems.sumOf { selectedItemId ->
            val cart = _uiState.value.carts.find { it.id == selectedItemId }
            val price = cart?.variant?.price ?: 0.0
            val sale = cart?.variant?.sale ?: 0.0
            val quantity = cart?.quantity ?: 0
            price * (1 - sale / 100) * quantity
        }
        return roundDouble(totalPrice)
    }

    private fun roundDouble(value: Double): Double {
        return BigDecimal(value).setScale(2, RoundingMode.HALF_EVEN).toDouble()
    }


}