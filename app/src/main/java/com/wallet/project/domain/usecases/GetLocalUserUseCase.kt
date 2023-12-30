package com.wallet.project.domain.usecases

import com.wallet.project.data.repositories.UserRepository
import com.wallet.project.domain.entities.User
import kotlinx.coroutines.flow.Flow

class GetLocalUserUseCase(private val userRepository: UserRepository) {
    operator fun invoke(): Flow<User?> {
        return userRepository.getUser()
    }
}
