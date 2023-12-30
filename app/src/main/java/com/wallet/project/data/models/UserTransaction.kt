package com.wallet.project.data.models

data class UserTransaction(
    val transactionAmount: String,
    val transactionDetail: String,
    val transactionNumber: Int,
    val transactionType: Int,
)
