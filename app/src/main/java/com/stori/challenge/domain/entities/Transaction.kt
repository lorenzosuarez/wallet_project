package com.stori.challenge.domain.entities

data class Transaction(
    val transactionAmount: String,
    val transactionDetail: String,
    val transactionNumber: Int,
    val transactionType: Int
)
