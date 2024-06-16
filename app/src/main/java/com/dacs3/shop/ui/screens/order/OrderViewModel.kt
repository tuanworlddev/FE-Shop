package com.dacs3.shop.ui.screens.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dacs3.shop.model.OrderStatus
import com.dacs3.shop.repository.AuthRepository
import com.dacs3.shop.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val authRepository: AuthRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(OrderUiState())
    val uiState: StateFlow<OrderUiState> = _uiState
    fun loadOrders() {
        viewModelScope.launch {
            try {
                if (isUserLoggedIn()) {
                    val response = orderRepository.getOrdersByUser()
                    if (response.isSuccessful) {
                        _uiState.value = _uiState.value.copy(orders = response.body() ?: emptyList())
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    fun onStatusChange(status: OrderStatus) {
        val orderFilter = _uiState.value.orders.filter { it.status == status }
        _uiState.value = _uiState.value.copy(orderFilter = orderFilter, selectedStatus = status)
    }

    private fun isUserLoggedIn(): Boolean {
        return authRepository.user != null
    }

}