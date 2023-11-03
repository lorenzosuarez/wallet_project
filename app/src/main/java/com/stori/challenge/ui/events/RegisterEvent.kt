package com.stori.challenge.ui.events

sealed class RegisterEvent {
    data class NameChanged(val name: String) : RegisterEvent()
    data class LastNameChanged(val lastName: String) : RegisterEvent()
    data class EmailChanged(val email: String) : RegisterEvent()
    data class PasswordChanged(val password: String) : RegisterEvent()
    data class VisiblePassword(val isVisiblePassword: Boolean) : RegisterEvent()
}
