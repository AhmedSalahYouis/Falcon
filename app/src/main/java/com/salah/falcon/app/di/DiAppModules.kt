package com.salah.falcon.app.di

import com.salah.falcon.app.logger.ITimberLogger
import com.salah.falcon.app.logger.TimberLogger
import org.koin.dsl.module

val appModule = module {
    single<ITimberLogger> { TimberLogger() }

}
