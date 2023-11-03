package com.stori.challenge.domain.usecases

import com.stori.challenge.domain.entities.User
import com.stori.challenge.domain.repositories.AuthRepository

class GetRemoteUserUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(uid: String): Result<User> {
        return authRepository.getUserProfile(uid)
    }
}
