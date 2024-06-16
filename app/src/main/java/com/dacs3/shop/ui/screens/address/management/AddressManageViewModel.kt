package com.dacs3.shop.ui.screens.address.management

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dacs3.shop.repository.AddressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressManageViewModel @Inject constructor(
    private val addressRepository: AddressRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddressManageUiState())
    val uiState: StateFlow<AddressManageUiState> = _uiState

    fun loadAddress() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                val response = addressRepository.getAddresses()
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(addressList = response.body() ?: emptyList(), isLoading = false)
                } else {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
            } catch (e: Exception) {
                // Handle error
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

}