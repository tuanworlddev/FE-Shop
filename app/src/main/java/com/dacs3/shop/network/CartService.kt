package com.dacs3.shop.network

import com.dacs3.shop.model.Cart
import com.dacs3.shop.model.CartRequest
import com.dacs3.shop.model.ResponseDto
import com.dacs3.shop.model.UpdateCart
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CartService {
    @GET("api/carts")
    suspend fun getCarts(): Response<List<Cart>>

    @POST("api/carts")
    suspend fun addCart(@Body cartRequest: CartRequest): Response<ResponseDto>

    @PUT("api/carts/{id}")
    suspend fun updateCart(@Path("id") id: Int, @Body updateCart: UpdateCart): Response<ResponseDto>

    @DELETE("api/carts/{id}")
    suspend fun deleteCart(@Path("id") id: Int): Response<ResponseDto>

}