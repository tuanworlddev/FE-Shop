package com.dacs3.shop.network

import com.dacs3.shop.model.ResponseDto
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Path

interface VariantService {
    @DELETE("api/product-variants/{id}")
    suspend fun deleteVariant(@Path("id") id: Int): Response<ResponseDto>
}