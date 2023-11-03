package com.stori.challenge.ui.viewmodels

import com.stori.challenge.ui.states.UiState
import com.stori.challenge.util.constants.EMAIL_REGEX

interface Validator {
    fun validate(input: String): UiState
}

class ValidatePassword {
    companion object : Validator {
        private const val MINIMUM_CHARACTERS = 6
        override fun validate(input: String) = if (input.trim().count() >= MINIMUM_CHARACTERS) UiState.Idle else UiState.Error
    }
}

class ValidateName {
    companion object : Validator {
        override fun validate(input: String) = if (input.isNotEmpty()) UiState.Idle else UiState.Error
    }
}

class ValidateEmail {
    companion object : Validator {
        override fun validate(input: String) = if (input.matches(EMAIL_REGEX.toRegex())) UiState.Idle else UiState.Error
    }
}

