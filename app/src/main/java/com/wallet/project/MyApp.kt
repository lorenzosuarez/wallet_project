package com.wallet.project

import android.app.Application
import com.wallet.project.di.modules.appModule
import com.wallet.project.di.modules.dataStoreModule
import com.wallet.project.di.modules.loggerModule
import com.wallet.project.di.modules.repositoryModule
import com.wallet.project.di.modules.viewModelsModule
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
