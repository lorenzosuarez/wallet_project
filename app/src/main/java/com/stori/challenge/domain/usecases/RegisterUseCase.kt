package com.stori.challenge.domain.usecases

import com.stori.challenge.domain.entities.User
import com.stori.challenge.domain.repositories.AuthRepository

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
