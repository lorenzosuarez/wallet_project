package com.stori.challenge.di.modules

import com.stori.challenge.ui.viewmodels.LoginViewModel
import com.stori.challenge.ui.viewmodels.MainViewModel
import com.stori.challenge.ui.viewmodels.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel {
        MainViewModel(
            getRemoteUserUseCase = get(),
            getTransactionsUseCase = get(),
            observeAuthStateUseCase = get(),
            logoutUseCase = get(),
            logger = get(),
        )
    }
    viewModel {
        LoginViewModel(
            loginUseCase = get(),
            logger = get(),
        )
    }
    viewModel {
        RegisterViewModel(
            registerUseCase = get(),
            uploadDocumentImageUseCase = get(),
            logger = get(),
        )
    }
}
