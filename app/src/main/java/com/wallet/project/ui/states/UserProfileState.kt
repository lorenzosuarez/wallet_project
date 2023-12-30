package com.wallet.project.ui.states

import com.wallet.project.data.models.UserProfile
import com.wallet.project.data.models.UserTransactionData

data class UserProfileState(
    val userProfile: UserProfile? = null,
    val userTransactionData: UserTransactionData? = UserTransactionData(),
    val isLoading: Boolean = false,
)
