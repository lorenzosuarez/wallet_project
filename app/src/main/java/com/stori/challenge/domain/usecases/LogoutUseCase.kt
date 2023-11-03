package com.stori.challenge.domain.usecases

import com.stori.challenge.domain.repositories.AuthRepository

class LogoutUseCase(private val authRepository: AuthRepository) {
    operator fun invoke() {
        authRepository.signOut()
    }
}
