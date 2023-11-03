package com.stori.challenge.domain.usecases

import com.stori.challenge.data.repositories.UserRepository
import com.stori.challenge.domain.entities.User
import kotlinx.coroutines.flow.Flow

class GetLocalUserUseCase(private val userRepository: UserRepository) {
    operator fun invoke(): Flow<User?> {
        return userRepository.getUser()
    }
}
