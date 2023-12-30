package com.wallet.project.domain.usecases

import com.wallet.project.data.repositories.UserRepository
import com.wallet.project.domain.entities.User

class SaveLocalUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(user: User) {
        userRepository.saveUser(user)
    }
}
