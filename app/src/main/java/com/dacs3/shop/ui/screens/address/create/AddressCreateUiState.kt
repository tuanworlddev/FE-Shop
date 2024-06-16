package com.dacs3.shop.ui.screens.address.create

data class AddressCreateUiState(
    val addressLine: String = "",
    val addressLineError: String? = null,
    val commune: String = "",
    val communeError: String? = null,
    val country: String = "",
    val countryError: String? = null,
    val district: String = "",
    val districtError: String? = null,
    val isDefault: Boolean = false,
    val phoneNumber: String = "",
    val phoneNumberError: String? = null,
    val province: String = "",
    val provinceError: String? = null,
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null
)
