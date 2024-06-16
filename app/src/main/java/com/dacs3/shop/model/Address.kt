package com.dacs3.shop.model

data class Address(
    val addressLine: String? = null,
    val commune: String? = null,
    val country: String? = null,
    val district: String? = null,
    val id: Int? = null,
    val isDefault: Boolean? = null,
    val phoneNumber: String? = null,
    val province: String? = null
)