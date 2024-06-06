package com.dacs3.shop.model

data class Variant(
    val color: Color? = null,
    val id: Int? = null,
    val price: Double? = null,
    val quantity: Int? = null,
    val sale: Double? = null,
    val size: Size? = null
)