package com.wallet.project.domain.repositories

import com.wallet.project.domain.entities.AuthenticationState
import com.wallet.project.domain.entities.User
import com.wallet.project.domain.entities.TransactionData
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(
        email: String,
        password: String,
        name: String,
        lastName: String,
    ): Result<User>

    suspend fun getUserProfile(uid: String): Result<User>
    suspend fun getTransactions(uid: String): Result<TransactionData>

    fun isUserLoggedIn(): Boolean
    fun signOut()
    fun getAuthState(): Flow<AuthenticationState>
}
