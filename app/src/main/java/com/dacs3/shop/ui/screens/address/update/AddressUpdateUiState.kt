package com.dacs3.shop.ui.screens.address.update

import com.dacs3.shop.model.Address

data class AddressUpdateUiState(
    val address: Address? = null,
    val isUpdating: Boolean = false,
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null
)
