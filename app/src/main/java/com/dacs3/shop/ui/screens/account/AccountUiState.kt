package com.dacs3.shop.ui.screens.account

import com.dacs3.shop.model.User

data class AccountUiState(
    val isLoggedIn: Boolean = false,
    val user: User? = null,
    val errorMessage: String? = null
)
