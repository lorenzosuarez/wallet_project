package com.wallet.project.domain.usecases

import com.wallet.project.domain.entities.User
import com.wallet.project.domain.repositories.AuthRepository

class RegisterUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(
        email: String,
        password: String,
        name: String,
        lastName: String,
    ): Result<User> {
        return authRepository.register(
            email = email,
            password = password,
            name = name,
            lastName = lastName,
        )
    }
}
