package com.dacs3.shop.ui.screens.order.manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dacs3.shop.model.OrderStatus
import com.dacs3.shop.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderManageViewModel @Inject constructor(
    private val orderRepository: OrderRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(OrderManageUiState())
    val uiState = _uiState

    fun loadAllOrders() {
        viewModelScope.launch {
            try {
                val response = orderRepository.getAllOrders()
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(orders = response.body() ?: emptyList(), orderFilter = response.body()!!.filter { it.status == OrderStatus.Pending })
                }
            } catch (e: Exception) {

            }
        }
    }

    fun onStatusChange(status: OrderStatus) {
        val orderFilter = _uiState.value.orders.filter { it.status == status }
        _uiState.value = _uiState.value.copy(orderFilter = orderFilter, selectedStatus = status)
    }
}