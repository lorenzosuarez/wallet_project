package com.wallet.project.domain.usecases

import com.wallet.project.domain.entities.User
import com.wallet.project.domain.repositories.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        return authRepository.login(email, password)
    }
}
