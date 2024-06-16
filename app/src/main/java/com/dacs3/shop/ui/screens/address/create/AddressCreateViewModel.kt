package com.dacs3.shop.ui.screens.address.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dacs3.shop.model.Address
import com.dacs3.shop.repository.AddressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressCreateViewModel @Inject constructor(
    private val addressRepository: AddressRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddressCreateUiState())
    val uiState: StateFlow<AddressCreateUiState> = _uiState

    fun onAddressLineChanged(addressLine: String) {
        _uiState.value = _uiState.value.copy(addressLine = addressLine)
    }

    fun onCommuneChanged(commune: String) {
        _uiState.value = _uiState.value.copy(commune = commune)
    }

    fun onDistrictChanged(district: String) {
        _uiState.value = _uiState.value.copy(district = district)
    }

    fun onProvinceChanged(province: String) {
        _uiState.value = _uiState.value.copy(province = province)
    }

    fun onCountryChanged(country: String) {
        _uiState.value = _uiState.value.copy(country = country)
    }

    fun onPhoneNumberChanged(phoneNumber: String) {
        _uiState.value = _uiState.value.copy(phoneNumber = phoneNumber)
    }

    fun onIsDefaultChanged(isDefault: Boolean) {
        _uiState.value = _uiState.value.copy(isDefault = isDefault)
    }

    fun onCreateAddress() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, successMessage = null, errorMessage = null)
            val currentUiState = _uiState.value
            var hasError = false

            if (currentUiState.addressLine.isBlank()) {
                hasError = true
            }

            if (currentUiState.commune.isBlank()) {
                hasError = true
            }

            if (currentUiState.district.isBlank()) {
                hasError = true
            }

            if (currentUiState.province.isBlank()) {
                hasError = true
            }

            if (currentUiState.country.isBlank()) {
                hasError = true
            }

            if (currentUiState.phoneNumber.isBlank()) {
                hasError = true
            }

            if (!hasError) {
                try {
                    val response = addressRepository.addAddress(
                        Address(
                            addressLine = currentUiState.addressLine,
                            commune = currentUiState.commune,
                            district = currentUiState.district,
                            province = currentUiState.province,
                            country = currentUiState.country,
                            phoneNumber = currentUiState.phoneNumber,
                            isDefault = currentUiState.isDefault
                        )
                    )
                    if (response.isSuccessful) {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            successMessage = response.body()?.message,
                            addressLine = "",
                            commune = "",
                            district = "",
                            province = "",
                            country = "",
                            phoneNumber = ""
                        )
                    }
                } catch (e: Exception) {
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = e.message)
                }
            } else {
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = "Please fill in all fields")
            }

        }
    }
}