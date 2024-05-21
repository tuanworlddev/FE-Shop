package com.dacs3.shop.network

import com.dacs3.shop.model.Category
import com.dacs3.shop.model.ResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CategoryService {
    @GET("api/categories")
    suspend fun getAllCategories(): Response<List<Category>>

    @GET("api/categories/{id}")
    suspend fun getCategoryById(@Path("id") id: Int): Response<Category>

    @POST("api/categories")
    suspend fun addCategory(@Body category: Category): Response<ResponseDto>

    @PUT("api/categories/{id}")
    suspend fun updateCategory(@Path("id") id: Int, @Body category: Category): Response<ResponseDto>

    @DELETE("api/categories/{id}")
    suspend fun deleteCategory(@Path("id") id: Int): Response<ResponseDto>
}