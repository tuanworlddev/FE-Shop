package com.dacs3.shop.model

data class AuthDto(
    val token: String,
    val refreshToken: String
)