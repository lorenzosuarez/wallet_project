package com.wallet.project.domain.usecases

import com.wallet.project.domain.repositories.AuthRepository

class CheckUserLoggedInUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(): Boolean = authRepository.isUserLoggedIn()
}
