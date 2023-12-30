package com.wallet.project.ui.states

enum class UiState {
    Loading,
    Idle,
    Disable,
    Error,
    None,
    ;

    val isEnabled get() = this == Idle
    val isLoading get() = this == Loading
    val isDisabled get() = this == Disable
    val isError get() = this == Error

    fun orNone(): UiState = if (this.isError) UiState.None else this
}
