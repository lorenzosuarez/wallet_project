package com.stori.challenge.domain.usecases

import com.stori.challenge.domain.repositories.AuthRepository

class CheckUserLoggedInUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(): Boolean = authRepository.isUserLoggedIn()
}
