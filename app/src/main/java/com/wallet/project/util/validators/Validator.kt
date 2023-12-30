package com.wallet.project.util.validators

import com.wallet.project.ui.states.UiState
import com.wallet.project.util.constants.EMAIL_REGEX

/**
 * An interface defining the contract for input validation.
 */
interface Validator {
    /**
     * Validates the given input string.
     *
     * @param input The input string to validate.
     * @return The [UiState] representing the result of the validation.
     */
    fun validate(input: String): UiState
}

/**
 * Validator for checking password strength and requirements.
 */
class ValidatePassword {
    companion object : Validator {
        private const val MINIMUM_CHARACTERS = 6

        /**
         * Validates if the input password meets the minimum length requirement.
         *
         * @param input The password string to validate.
         * @return [UiState.Idle] if the password meets the minimum length, [UiState.Error] otherwise.
         */
        override fun validate(input: String) = if (input.trim().count() >= MINIMUM_CHARACTERS) UiState.Idle else UiState.Error
    }
}

/**
 * Validator for checking if a name field is not empty.
 */
class ValidateName {
    companion object : Validator {
        /**
         * Validates if the input name is not empty.
         *
         * @param input The name string to validate.
         * @return [UiState.Idle] if the name is not empty, [UiState.Error] otherwise.
         */
        override fun validate(input: String) = if (input.isNotEmpty()) UiState.Idle else UiState.Error
    }
}

/**
 * Validator for checking if an email address is valid according to the EMAIL_REGEX pattern.
 */
class ValidateEmail {
    companion object : Validator {
        /**
         * Validates if the input email matches the required email format.
         *
         * @param input The email string to validate.
         * @return [UiState.Idle] if the email is valid, [UiState.Error] otherwise.
         */
        override fun validate(input: String) = if (input.matches(EMAIL_REGEX.toRegex())) UiState.Idle else UiState.Error
    }
}
