package com.wallet.project.di.modules

import com.wallet.project.util.constants.Logger
import org.koin.dsl.module

val loggerModule = module {
    single { Logger() }
}
