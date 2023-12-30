package com.wallet.project.di.modules

import com.wallet.project.domain.usecases.GetLocalUserUseCase
import com.wallet.project.domain.usecases.GetRemoteUserUseCase
import com.wallet.project.domain.usecases.GetTransactionsUseCase
import com.wallet.project.domain.usecases.LoginUseCase
import com.wallet.project.domain.usecases.LogoutUseCase
import com.wallet.project.domain.usecases.ObserveAuthStateUseCase
import com.wallet.project.domain.usecases.RegisterUseCase
import com.wallet.project.domain.usecases.SaveLocalUserUseCase
import com.wallet.project.domain.usecases.UploadDocumentImageUseCase
import org.koin.dsl.module

val appModule = module {
    // UseCases
    factory { LoginUseCase(get()) }
    factory { RegisterUseCase(get()) }
    factory { GetLocalUserUseCase(get()) }
    factory { SaveLocalUserUseCase(get()) }
    factory { ObserveAuthStateUseCase(get()) }
    factory { LogoutUseCase(get()) }
    factory { UploadDocumentImageUseCase() }
    factory { GetRemoteUserUseCase(get()) }
    factory { GetTransactionsUseCase(get()) }
}
