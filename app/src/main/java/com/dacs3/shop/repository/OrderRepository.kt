package com.dacs3.shop.repository

import com.dacs3.shop.model.OrderRequestDto
import com.dacs3.shop.model.UpdateStateOrderDto
import com.dacs3.shop.network.OrderService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepository @Inject constructor(
    private val orderService: OrderService
) {
    suspend fun getAllOrders() = orderService.getAllOrders()
    suspend fun getOrdersByUser() = orderService.getOrdersByUser()
    suspend fun createOrder(orderRequestDto: OrderRequestDto) = orderService.createOrder(orderRequestDto)
    suspend fun getOrder(id: Int) = orderService.getOrderDetails(id)

    suspend fun updateStatus(id: Int, updateStateOrderDto: UpdateStateOrderDto) = orderService.updateOrder(id, updateStateOrderDto)

    suspend fun deleteOrder(id: Int) = orderService.deleteOrder(id)

}