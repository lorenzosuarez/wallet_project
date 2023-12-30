package com.wallet.project.domain.usecases

import com.wallet.project.domain.entities.AuthenticationState
import com.wallet.project.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow

class ObserveAuthStateUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(): Flow<AuthenticationState> {
        return authRepository.getAuthState()
    }
}
