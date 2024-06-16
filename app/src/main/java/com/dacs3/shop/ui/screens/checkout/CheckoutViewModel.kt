package com.dacs3.shop.ui.screens.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dacs3.shop.model.Address
import com.dacs3.shop.repository.AddressRepository
import com.dacs3.shop.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    val cartRepository: CartRepository,
    val addressRepository: AddressRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CheckoutUiState())
    val uiState = _uiState

    fun loadCarts() {
        viewModelScope.launch {
            try {
                val response = cartRepository.getCarts()
                if (response.isSuccessful) {
                    val carts = response.body() ?: emptyList()
                    val subtotal = roundDouble(carts.sumOf { it.quantity!! * (1 - it.variant?.sale!! / 100) * it.variant.price!! })
                    val total = roundDouble(carts.sumOf { it.quantity!! * (1 - it.variant?.sale!! / 100) * it.variant.price!! } + _uiState.value.tax + _uiState.value.shippingCost)
                    _uiState.value = _uiState.value.copy(carts = carts, subtotal = subtotal, total = total)
                }
            } catch (e: Exception) {

            }
        }
    }

    fun loadAddresses() {
        viewModelScope.launch {
            try {
                val response = addressRepository.getAddresses()
                if (response.isSuccessful) {
                    val addresses = response.body() ?: emptyList()
                    _uiState.value = _uiState.value.copy(addresses = addresses)
                }
            } catch (e: Exception) {

            }
        }
    }

    fun selectAddress(address: Address) {
        _uiState.value = _uiState.value.copy(selectedAddress = address)
    }

    private fun roundDouble(value: Double): Double {
        return BigDecimal(value).setScale(2, RoundingMode.HALF_EVEN).toDouble()
    }
}