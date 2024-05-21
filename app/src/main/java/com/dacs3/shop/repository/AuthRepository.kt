package com.dacs3.shop.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.dacs3.shop.model.AuthDto
import com.dacs3.shop.model.LoginDto
import com.dacs3.shop.model.RefreshTokenDto
import com.dacs3.shop.model.User
import com.dacs3.shop.network.AuthService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(private val authService: AuthService) {
    var user: User? = null

    suspend fun getUser() = authService.getUserByToken()
    suspend fun login(loginDto: LoginDto) = authService.login(loginDto)
    suspend fun logout() = authService.logout()
    suspend fun refreshToken(refreshTokenDto: RefreshTokenDto) = authService.refreshToken(refreshTokenDto)

}