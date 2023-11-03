package com.stori.challenge.data.datasources.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.stori.challenge.domain.entities.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserLocalDataSource(private val dataStore: DataStore<Preferences>) {
    // Keys for the data store
    private val userIdKey = stringPreferencesKey("userIdKey")
    private val emailIdKey = stringPreferencesKey("emailIdKey")
    private val nameKey = stringPreferencesKey("nameKey")
    private val lastNameKey = stringPreferencesKey("lastNameKey")
    private val documentUrlKey = stringPreferencesKey("documentUrlKey")

    // Store user data
    suspend fun saveUser(user: User) {
        dataStore.edit { preferences ->
            preferences[userIdKey] = user.uid
            preferences[emailIdKey] = user.email
            preferences[nameKey] = user.name
            preferences[lastNameKey] = user.lastName
            preferences[documentUrlKey] = user.documentImageUrl
        }
    }

    // Get user data
    fun getUser(): Flow<User?> {
        return dataStore.data.map { preferences ->
            val uid = preferences[userIdKey] ?: return@map null
            val email = preferences[emailIdKey] ?: return@map null
            val name = preferences[nameKey] ?: return@map null
            val lastName = preferences[lastNameKey] ?: return@map null
            val documentImageUrl = preferences[documentUrlKey] ?: return@map null

            User(uid, email, name, lastName, documentImageUrl)
        }
    }
}
