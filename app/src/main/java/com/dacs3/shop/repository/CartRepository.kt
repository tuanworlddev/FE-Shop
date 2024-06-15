package com.dacs3.shop.repository

import com.dacs3.shop.model.CartRequest
import com.dacs3.shop.model.UpdateCart
import com.dacs3.shop.network.CartService
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val cartService: CartService
) {
    suspend fun getCarts() = cartService.getCarts()

    suspend fun addCart(cartRequest: CartRequest) = cartService.addCart(cartRequest)

    suspend fun updateCart(id: Int, updateCart: UpdateCart) = cartService.updateCart(id, updateCart)

    suspend fun deleteCart(id: Int) = cartService.deleteCart(id)

}