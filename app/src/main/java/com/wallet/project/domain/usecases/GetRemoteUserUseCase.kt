package com.wallet.project.domain.usecases

import com.wallet.project.domain.entities.User
import com.wallet.project.domain.repositories.AuthRepository

class GetRemoteUserUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(uid: String): Result<User> {
        return authRepository.getUserProfile(uid)
    }
}
