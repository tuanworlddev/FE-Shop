package com.dacs3.shop.repository

import com.dacs3.shop.network.ColorService
import javax.inject.Inject

class ColorRepository @Inject constructor(private val colorService: ColorService) {
    suspend fun getAllColors() = colorService.getAllColors()
}