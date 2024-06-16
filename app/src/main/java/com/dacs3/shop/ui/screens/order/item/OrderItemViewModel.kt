package com.dacs3.shop.ui.screens.order.item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dacs3.shop.model.OrderStatus
import com.dacs3.shop.model.UpdateStateOrderDto
import com.dacs3.shop.repository.OrderRepository
import com.dacs3.shop.ui.screens.order.details.OrderDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderItemViewModel @Inject constructor(
    private val orderRepository: OrderRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(OrderItemUiState())
    val uiState = _uiState

    fun getOrderDetails(orderId: Int) {
        viewModelScope.launch {
            try {
                val response = orderRepository.getOrder(orderId)
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(orderDetails = response.body(), selectedStatus = response.body()!!.status)
                }
            } catch (e: Exception) {

            }
        }
    }

    private fun onUpdateState(orderStatus: OrderStatus) {
        viewModelScope.launch {
            try {
                val id = _uiState.value.orderDetails?.id
                if (id != null) {
                    val response = orderRepository.updateStatus(id, UpdateStateOrderDto(orderStatus.name))
                    if (response.isSuccessful) {
                        _uiState.value = _uiState.value.copy(isCancelled = true)
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    fun onStatusChanged(status: OrderStatus) {
        if (status != _uiState.value.selectedStatus) {
            _uiState.value = _uiState.value.copy(selectedStatus = status)
            onUpdateState(status)
        }
    }
}