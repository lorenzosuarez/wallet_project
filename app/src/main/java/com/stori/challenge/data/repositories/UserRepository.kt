package com.stori.challenge.data.repositories

import com.stori.challenge.data.datasources.local.UserLocalDataSource
import com.stori.challenge.domain.entities.User
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
