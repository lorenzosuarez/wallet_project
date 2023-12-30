package com.wallet.project.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wallet.project.domain.usecases.LoginUseCase
import com.wallet.project.ui.events.LoginEvent
import com.wallet.project.ui.states.LoginState
import com.wallet.project.ui.states.MainResultSate
import com.wallet.project.ui.states.UiState
import com.wallet.project.util.constants.Logger
import com.wallet.project.util.validators.ValidateEmail
import com.wallet.project.util.validators.ValidatePassword
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val logger: Logger,
) : ViewModel() {
    var loginState by mutableStateOf(LoginState())

    private val _loginResultSate = MutableSharedFlow<MainResultSate>()
    val loginResultSate = _loginResultSate.asSharedFlow()

    fun login() {
        if (validateFields()) {
            viewModelScope.launch {
                isLoading = true

                loginUseCase(
                    email = loginState.email,
                    password = loginState.password,
                )
                    .onSuccess { isLoading = false }.onFailure { error ->
                        isLoading = false

                        logger.logException(error)
                        _loginResultSate.emit(MainResultSate.Failure(error.message))
                    }
            }
        }
    }

    private var isLoading by Delegates.observable(false) { _, _, newValue ->
        loginState = loginState.copy(isLoading = newValue)
    }

    private fun validateFields(): Boolean {
        loginState = loginState.copy(
            emailState = ValidateEmail.validate(loginState.email),
            passwordState = ValidatePassword.validate(loginState.password),
        )

        loginState.run {
            return listOf(
                emailState,
                passwordState,
            ).all(UiState::isEnabled)
        }
    }

    fun onEvent(event: LoginEvent) {
        loginState = when (event) {
            is LoginEvent.EmailChanged -> {
                loginState.copy(
                    email = event.email,
                    emailState = ValidateEmail.validate(event.email).orNone(),
                )
            }

            is LoginEvent.PasswordChanged -> {
                loginState.copy(
                    password = event.password,
                    passwordState = ValidatePassword.validate(event.password).orNone(),
                )
            }

            is LoginEvent.VisiblePassword -> {
                loginState.copy(isVisiblePassword = event.isVisiblePassword)
            }
        }
    }
}
