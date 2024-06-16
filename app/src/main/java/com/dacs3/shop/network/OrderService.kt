package com.dacs3.shop.network

import com.dacs3.shop.model.OrderDetails
import com.dacs3.shop.model.OrderRequestDto
import com.dacs3.shop.model.ResponseDto
import com.dacs3.shop.model.UpdateStateOrderDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderService {
    @GET("api/orders")
    suspend fun getAllOrders(): Response<List<OrderDetails>>

    @GET("api/orders/user")
    suspend fun getOrdersByUser(): Response<List<OrderDetails>>

    @GET("api/orders/{id}")
    suspend fun getOrderDetails(@Path("id") orderId: Int): Response<OrderDetails>

    @POST("api/orders")
    suspend fun createOrder(@Body orderRequestDto: OrderRequestDto): Response<ResponseDto>

    @PATCH("api/orders/{id}")
    suspend fun updateOrder(@Path("id") orderId: Int, @Body updateStateOrderDto: UpdateStateOrderDto): Response<ResponseDto>

    @DELETE("api/orders/{id}")
    suspend fun deleteOrder(@Path("id") orderId: Int): Response<ResponseDto>
}