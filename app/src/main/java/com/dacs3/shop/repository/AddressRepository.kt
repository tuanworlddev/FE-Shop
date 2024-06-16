package com.dacs3.shop.repository

import com.dacs3.shop.model.Address
import com.dacs3.shop.network.AddressService
import javax.inject.Inject

class AddressRepository @Inject constructor(
    private val addressService: AddressService
) {
    suspend fun getAddresses() = addressService.getAddresses()

    suspend fun getAddressById(id: Int) = addressService.getAddressById(id)

    suspend fun addAddress(address: Address) = addressService.addAddress(address)

    suspend fun updateAddress(id: Int, address: Address) = addressService.updateAddress(id, address)
}