package com.wallet.project.ui.viewmodels

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wallet.project.domain.usecases.RegisterUseCase
import com.wallet.project.domain.usecases.UploadDocumentImageUseCase
import com.wallet.project.ui.events.RegisterEvent
import com.wallet.project.ui.states.MainResultSate
import com.wallet.project.ui.states.RegisterState
import com.wallet.project.ui.states.UiState
import com.wallet.project.util.constants.Logger
import com.wallet.project.util.validators.ValidateEmail
import com.wallet.project.util.validators.ValidateName
import com.wallet.project.util.validators.ValidatePassword
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase,
    private val uploadDocumentImageUseCase: UploadDocumentImageUseCase,
    private val logger: Logger,
) : ViewModel() {
    var registerState by mutableStateOf(RegisterState())

    private val _registerResultState = MutableSharedFlow<MainResultSate>()
    val registerResultState = _registerResultState.asSharedFlow()

    private val _imageBitmap = MutableSharedFlow<Bitmap?>(replay = 1)
    val imageBitmap: SharedFlow<Bitmap?> = _imageBitmap.asSharedFlow()

    fun setImageBitmap(bitmap: Bitmap?) {
        viewModelScope.launch {
            bitmap?.let { btm ->
                _imageBitmap.tryEmit(btm)
            }
            registerState = registerState.copy(
                documentState = _imageBitmap.toUiState(),
            )
        }
    }

    fun register() {
        viewModelScope.launch {
            if (validateFields()) {
                isLoading = true

                registerUseCase(
                    email = registerState.email,
                    password = registerState.password,
                    name = registerState.name,
                    lastName = registerState.lastName,
                )
                    .onSuccess { user ->
                        isLoading = false
                        uploadDocumentImage(user.uid)
                    }.onFailure { error ->
                        isLoading = false

                        logger.logException(error)
                        _registerResultState.emit(MainResultSate.Failure(error.message))
                    }
            }
        }
    }

    private suspend fun uploadDocumentImage(uid: String) {
        _imageBitmap.replayCache.firstOrNull()?.let { bmp ->
            uploadDocumentImageUseCase.execute(
                uid = uid,
                documentImageBitmap = bmp,
            )
        }
    }

    private var isLoading by Delegates.observable(false) { _, _, newValue ->
        registerState = registerState.copy(isLoading = newValue)
    }

    private fun SharedFlow<Bitmap?>.toUiState(): UiState = replayCache.firstOrNull()
        ?.takeIf { true }
        ?.let { UiState.Idle }
        ?: UiState.Error

    private fun validateFields(): Boolean {
        registerState = registerState.copy(
            nameState = ValidateName.validate(registerState.name),
            lastNameState = ValidateName.validate(registerState.lastName),
            emailState = ValidateEmail.validate(registerState.email),
            passwordState = ValidatePassword.validate(registerState.password),
            documentState = _imageBitmap.toUiState(),
        )

        registerState.run {
            return listOf(
                nameState,
                lastNameState,
                emailState,
                passwordState,
                documentState,
            ).all(UiState::isEnabled)
        }
    }

    fun onEvent(event: RegisterEvent) {
        registerState = when (event) {
            is RegisterEvent.NameChanged -> {
                registerState.copy(
                    name = event.name,
                    nameState = ValidateName.validate(event.name).orNone(),
                )
            }

            is RegisterEvent.LastNameChanged -> {
                registerState.copy(
                    lastName = event.lastName,
                    lastNameState = ValidateName.validate(event.lastName).orNone(),
                )
            }

            is RegisterEvent.EmailChanged -> {
                registerState.copy(
                    email = event.email,
                    emailState = ValidateEmail.validate(event.email).orNone(),
                )
            }

            is RegisterEvent.PasswordChanged -> {
                registerState.copy(
                    password = event.password,
                    passwordState = ValidatePassword.validate(event.password).orNone(),
                )
            }

            is RegisterEvent.VisiblePassword -> {
                registerState.copy(isVisiblePassword = event.isVisiblePassword)
            }
        }
    }
}
