package com.wallet.project.data.repositories

import com.wallet.project.data.datasources.local.UserLocalDataSource
import com.wallet.project.domain.entities.User
import kotlinx.coroutines.flow.Flow

class UserRepository(private val localDataSource: UserLocalDataSource) {
    // Store user data
    suspend fun saveUser(user: User) {
        localDataSource.saveUser(user)
    }

    // Get user data
    fun getUser(): Flow<User?> {
        return localDataSource.getUser()
    }
}
