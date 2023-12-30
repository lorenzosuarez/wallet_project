package com.wallet.project.data.models

data class UserTransactionData(
    val amount: Double = 0.0,
    val transactions: List<UserTransaction> = arrayListOf(),
)
