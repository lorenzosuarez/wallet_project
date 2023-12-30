package com.wallet.project.domain.usecases

import com.wallet.project.domain.repositories.AuthRepository

class LogoutUseCase(private val authRepository: AuthRepository) {
    operator fun invoke() {
        authRepository.signOut()
    }
}
