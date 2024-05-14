package com.dacs3.shop.network

import com.dacs3.shop.model.ResponseDto
import com.dacs3.shop.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserService {
    @GET("api/users")
    suspend fun getAllUsers(): Response<List<User>>

    @POST("api/users")
    suspend fun addUser(@Body user: User): Response<ResponseDto>

    @PUT("api/users/{id}")
    suspend fun updateUser(@Path("id") id: Int, @Body user: User): Response<ResponseDto>

    @DELETE("api/users/{id}")
    suspend fun deleteUser(@Path("id") id: Int): Response<ResponseDto>
}