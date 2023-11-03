package com.stori.challenge.ui.states

sealed class MainResultSate {
    object Idle : MainResultSate()
    data class Success<out T>(val data: T) : MainResultSate()
    data class Failure(val errorMessage: String?) : MainResultSate()
}
