package com.stori.challenge.domain.usecases

import com.stori.challenge.domain.entities.AuthenticationState
import com.stori.challenge.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow

class ObserveAuthStateUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(): Flow<AuthenticationState> {
        return authRepository.getAuthState()
    }
}
