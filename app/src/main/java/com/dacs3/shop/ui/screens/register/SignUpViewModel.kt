package com.dacs3.shop.ui.screens.register

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dacs3.shop.model.User
import com.dacs3.shop.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    private val _signUpUiState = MutableStateFlow(SignUpUiState())
    val signUpUiState: StateFlow<SignUpUiState> = _signUpUiState

    fun onFirstNameChange(firstName: String) {
        _signUpUiState.value = _signUpUiState.value.copy(firstName = firstName, firstNameError = null)
    }

    fun onLastNameChange(lastName: String) {
        _signUpUiState.value = _signUpUiState.value.copy(lastName = lastName, lastNameError = null)
    }

    fun onEmailChange(email: String) {
        _signUpUiState.value = _signUpUiState.value.copy(email = email, emailError = null)
    }

    fun onPasswordChange(password: String) {
        _signUpUiState.value = _signUpUiState.value.copy(password = password, passwordError = null)
    }

    fun signUp() {
        viewModelScope.launch {
            val currentState = _signUpUiState.value
            var hasError = false

            var firstNameError: String? = null
            var lastNameError: String? = null
            var emailError: String? = null
            var passwordError: String? = null

            if (currentState.firstName.isBlank()) {
                firstNameError = "First name is required"
                hasError = true
            }

            if (currentState.lastName.isBlank()) {
                lastNameError = "Last name is required"
                hasError = true
            }

            if (currentState.email.isBlank()) {
                emailError = "Email is required"
                hasError = true
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(currentState.email).matches()) {
                emailError = "Invalid email format"
                hasError = true
            }

            if (currentState.password.isBlank()) {
                passwordError = "Password is required"
                hasError = true
            } else if (currentState.password.length < 6) {
                passwordError = "Password must be at least 6 characters"
                hasError = true
            }

            if (hasError) {
                _signUpUiState.value = currentState.copy(
                    firstNameError = firstNameError,
                    lastNameError = lastNameError,
                    emailError = emailError,
                    passwordError = passwordError
                )
            } else {
                val response = userRepository.addUser(User(firstName = currentState.firstName, lastName = currentState.lastName, email = currentState.email, password = currentState.password))
                if (response.isSuccessful) {

                }
            }
        }
    }
}