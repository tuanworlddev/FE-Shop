package com.dacs3.shop.repository

import com.dacs3.shop.network.VariantService
import javax.inject.Inject

class VariantRepository @Inject constructor(private val variantService: VariantService) {
    suspend fun deleteVariant(id: Int) = variantService.deleteVariant(id)
}