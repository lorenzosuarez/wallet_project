package com.stori.challenge.domain.usecases

import com.stori.challenge.data.repositories.UserRepository
import com.stori.challenge.domain.entities.User

class SaveLocalUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(user: User) {
        userRepository.saveUser(user)
    }
}
