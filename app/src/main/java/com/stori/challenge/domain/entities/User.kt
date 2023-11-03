package com.stori.challenge.domain.entities

data class User(
    val uid: String,
    val email: String,
    val name: String,
    val lastName: String,
    val documentImageUrl: String,
) {
    fun hasUid(): Boolean = this.uid.isNotEmpty()
}
