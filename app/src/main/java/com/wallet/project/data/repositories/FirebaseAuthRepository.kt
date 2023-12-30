package com.wallet.project.data.repositories

import com.wallet.project.data.datasources.remote.RemoteDataSource
import com.wallet.project.domain.entities.AuthenticationState
import com.wallet.project.domain.entities.User
import com.wallet.project.domain.entities.TransactionData
import com.wallet.project.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow

class FirebaseAuthRepository(
    private val remoteDataSource: RemoteDataSource,
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<User> =
        remoteDataSource.login(
            email,
            password,
        )

    override suspend fun register(
        email: String,
        password: String,
        name: String,
        lastName: String,
    ): Result<User> = remoteDataSource.register(
        email = email,
        password = password,
        name = name,
        lastName = lastName,
    )

    override suspend fun getUserProfile(uid: String): Result<User> =
        remoteDataSource.getUserProfile(uid = uid)

    override suspend fun getTransactions(uid: String): Result<TransactionData> =
        remoteDataSource.getTransactions(uid = uid)

    override fun isUserLoggedIn(): Boolean = remoteDataSource.isUserLoggedIn()

    override fun signOut() {
        remoteDataSource.signOut()
    }

    override fun getAuthState(): Flow<AuthenticationState> = remoteDataSource.getAuthState()
}
