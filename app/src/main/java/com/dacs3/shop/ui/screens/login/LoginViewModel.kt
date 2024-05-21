package com.dacs3.shop.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dacs3.shop.model.AuthDto
import com.dacs3.shop.model.LoginDto
import com.dacs3.shop.model.ResponseDto
import com.dacs3.shop.repository.AuthRepository
import com.dacs3.shop.repository.DataStoreRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState: StateFlow<LoginUiState> = _loginUiState

    fun onEmailChange(email: String) {
        _loginUiState.value = _loginUiState.value.copy(email = email, emailError = null)
    }

    fun onPasswordChange(password: String) {
        _loginUiState.value = _loginUiState.value.copy(password = password, passwordError = null)
    }

    fun login() {
        viewModelScope.launch {
            val currentUiState = _loginUiState.value
            var hasError = false

            var emailError: String? = null
            var passwordError: String? = null

            if (currentUiState.email.isBlank()) {
                emailError = "Email is required"
                hasError = true
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(currentUiState.email).matches()) {
                emailError = "Invalid email format"
                hasError = true
            }

            if (currentUiState.password.isBlank()) {
                passwordError = "Password is required"
                hasError = true
            } else if (currentUiState.password.length < 6) {
                passwordError = "Password must be at least 6 characters"
                hasError = true
            }

            if (hasError) {
                _loginUiState.value = currentUiState.copy(emailError = emailError, passwordError = passwordError)
            } else {
                try {
                    _loginUiState.value = _loginUiState.value.copy(isLoading = true, errorMessage = null)
                    val response = authRepository.login(LoginDto(currentUiState.email, currentUiState.password))
                    if (response.isSuccessful) {
                        val authDto: AuthDto? = response.body()
                        dataStoreRepository.saveToken(authDto?.token!!, authDto.refreshToken)
                        _loginUiState.value = _loginUiState.value.copy(isLoading = false, loginSuccess = true)
                    } else {
                        val gson = Gson()
                        val errorJson = response.errorBody()?.string()
                        val errorResponse = gson.fromJson(errorJson, ResponseDto::class.java)
                        _loginUiState.value = _loginUiState.value.copy(errorMessage = errorResponse.message, isLoading = false, loginSuccess = false)
                    }
                } catch (e: Exception) {
                    _loginUiState.value = _loginUiState.value.copy(errorMessage = e.message, isLoading = false, loginSuccess = false)
                }
            }
        }
    }
}