package com.stori.challenge.domain.entities

data class TransactionData(
    val amount: Double,
    val transactions: List<Transaction>,
)
