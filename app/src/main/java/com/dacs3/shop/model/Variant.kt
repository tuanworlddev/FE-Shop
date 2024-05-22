package com.dacs3.shop.model

data class Variant(
    val color: Color,
    val id: Int,
    val price: Double,
    val quantity: Int,
    val sale: Double,
    val size: String
)