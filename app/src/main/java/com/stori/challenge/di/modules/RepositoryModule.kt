package com.stori.challenge.di.modules

import com.stori.challenge.data.datasources.local.UserLocalDataSource
import com.stori.challenge.data.datasources.remote.RemoteDataSource
import com.stori.challenge.data.repositories.FirebaseAuthRepository
import com.stori.challenge.data.repositories.UserRepository
import com.stori.challenge.domain.repositories.AuthRepository
import com.stori.challenge.domain.usecases.LoginUseCase
import org.koin.dsl.module

val repositoryModule = module {
    single { RemoteDataSource() }
    single { UserLocalDataSource(get()) }
    single { UserRepository(get()) }
    single<AuthRepository> { FirebaseAuthRepository(remoteDataSource = get()) }
}
