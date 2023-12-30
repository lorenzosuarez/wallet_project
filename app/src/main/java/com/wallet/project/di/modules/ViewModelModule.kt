package com.wallet.project.di.modules

import com.wallet.project.ui.viewmodels.LoginViewModel
import com.wallet.project.ui.viewmodels.MainViewModel
import com.wallet.project.ui.viewmodels.RegisterViewModel
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
