package com.dacs3.shop.ui.screens.order.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dacs3.shop.model.OrderStatus
import com.dacs3.shop.model.UpdateStateOrderDto
import com.dacs3.shop.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    private val orderRepository: OrderRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(OrderDetailsUiState())
    val uiState: StateFlow<OrderDetailsUiState> = _uiState

    fun getOrderDetails(orderId: Int) {
        viewModelScope.launch {
            try {
                val response = orderRepository.getOrder(orderId)
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(orderDetails = response.body())
                }
            } catch (e: Exception) {

            }
        }
    }

    fun onViewAllClick() {
        _uiState.value = _uiState.value.copy(isViewAll = !_uiState.value.isViewAll)
    }

    fun updateOrderStatus(orderStatus: OrderStatus) {
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
}