package com.dacs3.shop.network

import com.dacs3.shop.model.Address
import com.dacs3.shop.model.ResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AddressService {
    @GET("api/addresses")
    suspend fun getAddresses(): Response<List<Address>>

    @GET("api/addresses/{id}")
    suspend fun getAddressById(@Path("id") id: Int): Response<Address>

    @POST("api/addresses")
    suspend fun addAddress(@Body address: Address): Response<ResponseDto>

    @PUT("api/addresses/{id}")
    suspend fun updateAddress(@Path("id") id: Int, @Body address: Address): Response<ResponseDto>


}