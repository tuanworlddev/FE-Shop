package com.dacs3.shop.model

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val category: String,
    val images: List<Image>,
    val variants: List<Variant>,
    val createdAt: String,
    val updatedAt: String
)