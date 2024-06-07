package com.dacs3.shop.ui.screens.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dacs3.shop.repository.AuthRepository
import com.dacs3.shop.repository.DataStoreRepository
import com.dacs3.shop.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _accountUiState = MutableStateFlow(AccountUiState())
    val accountUiState: StateFlow<AccountUiState> = _accountUiState

    fun loadUser() {
        viewModelScope.launch {
            if (dataStoreRepository.token.first() != null && _accountUiState.value.user == null && authRepository.user == null) {
                try {
                    val result = authRepository.getUser()
                    if (result.isSuccessful) {
                        val user = result.body()
                        if (user != null) {
                            _accountUiState.value = _accountUiState.value.copy(user = user, isLoggedIn = true)
                            authRepository.user = user
                        } else {
                            _accountUiState.value = _accountUiState.value.copy(errorMessage = "User data is null")
                        }
                    } else {
                        _accountUiState.value = _accountUiState.value.copy(errorMessage = "Failed to load user")
                    }
                } catch (e: Exception) {
                    _accountUiState.value = _accountUiState.value.copy(errorMessage = e.message)
                }
            } else if (authRepository.user != null) {
                _accountUiState.value = _accountUiState.value.copy(user = authRepository.user, isLoggedIn = true)
            }
        }
    }

    fun onChangeErrorMessage(errorMessage: String?) {
        _accountUiState.value = _accountUiState.value.copy(errorMessage = errorMessage)
    }

    fun logout() {
        viewModelScope.launch {
            try {
                dataStoreRepository.clearTokens()
                _accountUiState.value = _accountUiState.value.copy(user = null, isLoggedIn = false)
                authRepository.user = null
                authRepository.logout()
            } catch (e: Exception) {
                _accountUiState.value = _accountUiState.value.copy(errorMessage = e.message)
            }

        }
    }
}