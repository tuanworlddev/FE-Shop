package com.dacs3.shop.network

import com.dacs3.shop.model.Size
import retrofit2.Response
import retrofit2.http.GET

interface SizeService {

    @GET("/api/sizes")
    suspend fun getAllSizes(): Response<List<Size>>
}