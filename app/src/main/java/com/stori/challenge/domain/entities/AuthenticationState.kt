package com.stori.challenge.domain.entities

sealed class AuthenticationState {
    object Initial : AuthenticationState()
    object Unauthenticated : AuthenticationState()
    data class Authenticated(val uid: String) : AuthenticationState()
}