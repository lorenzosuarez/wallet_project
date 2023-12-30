package com.wallet.project.di.modules

import com.wallet.project.data.datasources.local.UserLocalDataSource
import com.wallet.project.data.datasources.remote.RemoteDataSource
import com.wallet.project.data.repositories.FirebaseAuthRepository
import com.wallet.project.data.repositories.UserRepository
import com.wallet.project.domain.repositories.AuthRepository
import com.wallet.project.domain.usecases.LoginUseCase
import org.koin.dsl.module

val repositoryModule = module {
    single { RemoteDataSource() }
    single { UserLocalDataSource(get()) }
    single { UserRepository(get()) }
    single<AuthRepository> { FirebaseAuthRepository(remoteDataSource = get()) }
}
