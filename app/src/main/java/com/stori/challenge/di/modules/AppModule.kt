package com.stori.challenge.di.modules

import com.stori.challenge.domain.usecases.GetLocalUserUseCase
import com.stori.challenge.domain.usecases.GetRemoteUserUseCase
import com.stori.challenge.domain.usecases.GetTransactionsUseCase
import com.stori.challenge.domain.usecases.LoginUseCase
import com.stori.challenge.domain.usecases.LogoutUseCase
import com.stori.challenge.domain.usecases.ObserveAuthStateUseCase
import com.stori.challenge.domain.usecases.RegisterUseCase
import com.stori.challenge.domain.usecases.SaveLocalUserUseCase
import com.stori.challenge.domain.usecases.UploadDocumentImageUseCase
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
