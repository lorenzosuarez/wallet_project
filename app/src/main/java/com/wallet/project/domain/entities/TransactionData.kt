package com.wallet.project.domain.entities

data class TransactionData(
    val amount: Double,
    val transactions: List<Transaction>,
)
