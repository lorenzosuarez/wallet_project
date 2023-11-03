package com.stori.challenge

import android.app.Application
import com.stori.challenge.di.modules.appModule
import com.stori.challenge.di.modules.dataStoreModule
import com.stori.challenge.di.modules.loggerModule
import com.stori.challenge.di.modules.repositoryModule
import com.stori.challenge.di.modules.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(
                listOf(
                    appModule,
                    repositoryModule,
                    viewModelsModule,
                    dataStoreModule,
                    loggerModule,
                ),
            )
        }
    }
}
