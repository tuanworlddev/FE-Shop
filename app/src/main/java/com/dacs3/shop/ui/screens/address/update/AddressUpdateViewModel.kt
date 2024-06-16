package com.dacs3.shop.ui.screens.address.update

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dacs3.shop.repository.AddressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressUpdateViewModel @Inject constructor(
    private val addressRepository: AddressRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(AddressUpdateUiState())
    val uiState = _uiState

    fun loadAddress(id: Int) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                val response = addressRepository.getAddressById(id)
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(address = response.body(), isLoading = false)
                } else {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun onAddressLineChanged(addressLine: String) {
        _uiState.value = _uiState.value.copy(
            address = _uiState.value.address?.copy(addressLine = addressLine)
        )
    }

    fun onCommuneChanged(commune: String) {
        _uiState.value = _uiState.value.copy(
            address = _uiState.value.address?.copy(commune = commune)
        )
    }

    fun onDistrictChanged(district: String) {
        _uiState.value = _uiState.value.copy(
            address = _uiState.value.address?.copy(district = district)
        )
    }

    fun onProvinceChanged(province: String) {
        _uiState.value = _uiState.value.copy(
            address = _uiState.value.address?.copy(province = province)
        )
    }

    fun onCountryChanged(country: String) {
        _uiState.value = _uiState.value.copy(
            address = _uiState.value.address?.copy(country = country)
        )
    }

    fun onPhoneNumberChanged(phoneNumber: String) {
        _uiState.value = _uiState.value.copy(
            address = _uiState.value.address?.copy(phoneNumber = phoneNumber)
        )
    }

    fun onIsDefaultChanged(isDefault: Boolean) {
        _uiState.value = _uiState.value.copy(
            address = _uiState.value.address?.copy(isDefault = isDefault)
        )
    }

    fun onUpdateAddress() {
        viewModelScope.launch {
            try {
                val currentAddress = _uiState.value.address!!
                var hasError = false
                _uiState.value = _uiState.value.copy(isUpdating = true, successMessage = null, errorMessage = null)

                if (currentAddress.addressLine!!.isBlank()) {
                    hasError = true
                }

                if (currentAddress.commune!!.isBlank()) {
                    hasError = true
                }

                if (currentAddress.district!!.isBlank()) {
                    hasError = true
                }

                if (currentAddress.province!!.isBlank()) {
                    hasError = true
                }

                if (currentAddress.country!!.isBlank()) {
                    hasError = true
                }

                if (currentAddress.phoneNumber!!.isBlank()) {
                    hasError = true
                }

                if (!hasError) {
                    val response = addressRepository.updateAddress(currentAddress.id!!, currentAddress)
                    if (response.isSuccessful) {
                        _uiState.value = _uiState.value.copy(isUpdating = false, successMessage = "Address updated successfully", errorMessage = null)
                    } else {
                        _uiState.value = _uiState.value.copy(isUpdating = false, successMessage = null, errorMessage = "Failed to update address")
                    }
                } else {
                    _uiState.value = _uiState.value.copy(isUpdating = false, successMessage = null, errorMessage = "Please fill in all fields")
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isUpdating = false, successMessage = null, errorMessage = e.message)
            }
        }
    }
}