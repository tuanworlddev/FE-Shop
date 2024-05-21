package com.dacs3.shop.network

import com.dacs3.shop.model.Product
import com.dacs3.shop.model.ResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductService {
    @GET("api/products")
    suspend fun getAllProducts(): Response<List<Product>>

    @GET("api/products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Response<Product>

    @GET("api/products/category/{id}")
    suspend fun getProductByCategory(@Path("id") id: Int): Response<List<Product>>

    @POST("api/products")
    suspend fun addProduct(product: Product): Response<ResponseDto>

    @PUT("api/products/{id}")
    suspend fun updateProduct(@Path("id") id: Int, @Body product: Product): Response<ResponseDto>

    @DELETE("api/products/{id}")
    suspend fun deleteProduct(@Path("id") id: Int): Response<ResponseDto>
}