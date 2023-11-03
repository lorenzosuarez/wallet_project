package com.stori.challenge.ui.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stori.challenge.domain.entities.AuthenticationState
import com.stori.challenge.domain.usecases.GetRemoteUserUseCase
import com.stori.challenge.domain.usecases.GetTransactionsUseCase
import com.stori.challenge.domain.usecases.LogoutUseCase
import com.stori.challenge.domain.usecases.ObserveAuthStateUseCase
import com.stori.challenge.ui.events.MainEvent
import com.stori.challenge.ui.states.UserProfileState
import com.stori.challenge.util.constants.Logger
import com.stori.challenge.util.mappers.toUserProfile
import com.stori.challenge.util.mappers.toUserTransactionData
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    observeAuthStateUseCase: ObserveAuthStateUseCase,
    private val getRemoteUserUseCase: GetRemoteUserUseCase,
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val logger: Logger,
) : ViewModel() {
    val eventFlow = MutableSharedFlow<MainEvent>()
    private val _userProfileState = mutableStateOf(UserProfileState())
    val userProfileState: State<UserProfileState> get() = _userProfileState
    val authenticationState: StateFlow<AuthenticationState> = observeAuthStateUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = AuthenticationState.Initial,
    )

    fun triggerEvent(event: MainEvent) {
        viewModelScope.launch {
            eventFlow.emit(event)
        }
    }

    fun signOut() {
        runCatching { logoutUseCase.invoke() }.onFailure { error ->
            logger.logException(error)
        }
    }

    fun loadUserProfile(uid: String) {
        viewModelScope.launch {
            _userProfileState.value = UserProfileState(isLoading = true)

            val userProfile = async { getRemoteUserUseCase.invoke(uid = uid) }
            val transactions = async { getTransactionsUseCase.invoke(uid = uid) }

            delay(3000)

            _userProfileState.value = UserProfileState(
                userProfile = userProfile.await().getOrNull()?.toUserProfile(),
                userTransactionData = transactions.await().getOrNull()?.toUserTransactionData(),
                isLoading = false,
            )
        }
    }

    init {
        authenticationState.onEach { state ->
            when(state) {
                is AuthenticationState.Authenticated -> { loadUserProfile(state.uid) }
                else -> Unit
            }
        }.launchIn(viewModelScope)
    }
}
