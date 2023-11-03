package com.stori.challenge.ui.states

import com.stori.challenge.data.models.UserProfile
import com.stori.challenge.data.models.UserTransactionData

data class UserProfileState(
    val userProfile: UserProfile? = null,
    val userTransactionData: UserTransactionData? = UserTransactionData(),
    val isLoading: Boolean = false,
)
