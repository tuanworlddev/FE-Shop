package com.dacs3.shop.ui.screens.address.management

import com.dacs3.shop.model.Address

data class AddressManageUiState(
    val addressList: List<Address> = emptyList(),
    val isLoading: Boolean = false,
)
