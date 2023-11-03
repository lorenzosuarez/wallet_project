package com.stori.challenge.domain.usecases

import com.stori.challenge.domain.entities.TransactionData
import com.stori.challenge.domain.repositories.AuthRepository

class GetTransactionsUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(uid: String): Result<TransactionData> {
        return authRepository.getTransactions(uid)
    }
}
