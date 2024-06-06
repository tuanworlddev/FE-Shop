package com.dacs3.shop.network

import com.dacs3.shop.model.Color
import retrofit2.Response
import retrofit2.http.GET

interface ColorService {
    @GET("api/colors")
    suspend fun getAllColors(): Response<List<Color>>
}