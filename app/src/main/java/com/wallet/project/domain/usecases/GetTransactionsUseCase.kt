package com.wallet.project.domain.usecases

import com.wallet.project.domain.entities.TransactionData
import com.wallet.project.domain.repositories.AuthRepository

class GetTransactionsUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(uid: String): Result<TransactionData> {
        return authRepository.getTransactions(uid)
    }
}
