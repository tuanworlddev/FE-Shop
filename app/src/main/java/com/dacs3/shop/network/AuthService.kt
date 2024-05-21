package com.dacs3.shop.network

import com.dacs3.shop.model.AuthDto
import com.dacs3.shop.model.LoginDto
import com.dacs3.shop.model.RefreshTokenDto
import com.dacs3.shop.model.ResponseDto
import com.dacs3.shop.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {
    @GET("api/auth/user")
    suspend fun getUserByToken(): Response<User>

    @GET("api/auth/logout")
    suspend fun logout(): Response<ResponseDto>

    @POST("api/auth/login")
    suspend fun login(@Body loginDto: LoginDto): Response<AuthDto>

    @POST("api/auth/refresh-token")
    suspend fun refreshToken(@Body refreshToken: RefreshTokenDto): Response<AuthDto>
}