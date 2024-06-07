package com.dacs3.shop.model

data class ProductDto(
    val name: String,
    val description: String,
    val categoryId: Int,
    val images: List<String>,
    val variants: List<VariantDto>
)