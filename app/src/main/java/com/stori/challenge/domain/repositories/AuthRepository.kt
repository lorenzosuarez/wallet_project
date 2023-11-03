package com.stori.challenge.domain.repositories

import com.stori.challenge.domain.entities.AuthenticationState
import com.stori.challenge.domain.entities.User
import com.stori.challenge.domain.entities.TransactionData
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
