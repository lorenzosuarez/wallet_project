package com.stori.challenge.di.modules

import com.stori.challenge.util.constants.Logger
import org.koin.dsl.module

val loggerModule = module {
    single { Logger() }
}
