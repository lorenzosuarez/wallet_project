package com.stori.challenge.domain.usecases

import com.stori.challenge.domain.entities.User
import com.stori.challenge.domain.repositories.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        return authRepository.login(email, password)
    }
}
