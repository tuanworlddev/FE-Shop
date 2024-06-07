package com.dacs3.shop.model

data class VariantDto(
    val sizeId: Int,
    val colorId: Int,
    val price: Double,
    val quantity: Int,
    val sale: Double
)
