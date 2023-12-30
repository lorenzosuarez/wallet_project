package com.wallet.project.ui.events

sealed class LoginEvent {
    data class EmailChanged(val email: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    data class VisiblePassword(val isVisiblePassword: Boolean) : LoginEvent()
}
