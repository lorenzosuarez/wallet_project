package com.wallet.project.ui.states

data class RegisterState(
    val name: String = "",
    val nameState: UiState = UiState.None,
    val lastName: String = "",
    val lastNameState: UiState = UiState.None,
    val email: String = "",
    val emailState: UiState = UiState.None,
    val documentState: UiState = UiState.None,
    val password: String = "",
    val passwordState: UiState = UiState.None,
    val isVisiblePassword: Boolean = false,
    val isLoading: Boolean = false,
)
