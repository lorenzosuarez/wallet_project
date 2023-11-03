package com.stori.challenge.di.modules

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val Context.dataStore by preferencesDataStore(name = "user_prefs")

val dataStoreModule = module {
    single { provideDataStore(androidContext()) }
}

private fun provideDataStore(context: Context) = context.dataStore
