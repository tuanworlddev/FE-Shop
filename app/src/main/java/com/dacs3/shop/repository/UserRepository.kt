package com.dacs3.shop.repository

import com.dacs3.shop.model.User
import com.dacs3.shop.network.UserService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val userService: UserService) {
    suspend fun getAllUsers() = userService.getAllUsers()
    suspend fun addUser(user: User) = userService.addUser(user)
    suspend fun updateUser(id: Int, user: User) = userService.updateUser(id, user)
    suspend fun deleteUser(id: Int) = userService.deleteUser(id)
}