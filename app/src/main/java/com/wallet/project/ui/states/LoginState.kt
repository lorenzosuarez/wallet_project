package com.wallet.project.ui.states

data class LoginState(
    val email: String = "",
    val emailState: UiState = UiState.None,
    val password: String = "",
    val passwordState: UiState = UiState.None,
    val isVisiblePassword: Boolean = false,
    val isLoading: Boolean = false,
)
