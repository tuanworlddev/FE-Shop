package com.dacs3.shop.repository

import com.dacs3.shop.network.SizeService
import javax.inject.Inject

class SizeRepository @Inject constructor(private val sizeService: SizeService) {
    suspend fun getAllSizes() = sizeService.getAllSizes()
}