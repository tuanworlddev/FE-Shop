package com.dacs3.shop.network

import com.dacs3.shop.model.AuthDto
import com.dacs3.shop.model.LoginDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("api/auth/login")
    suspend fun login(@Body loginDto: LoginDto): Response<AuthDto>

    @POST("api/auth/refresh-token")
    suspend fun refreshToken(@Body refreshToken: String): Response<AuthDto>
}