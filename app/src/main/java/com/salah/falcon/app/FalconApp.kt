package com.salah.falcon.app

import android.app.Application
import com.salah.falcon.app.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FalconApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initDi()
    }

    private fun initDi() {
        startKoin {
            androidContext(this@FalconApp)
            // If you mistakenly define the same dependency multiple times, Koin will fail fast during startup, making it easier to identify and fix the issue
            allowOverride(false)

            modules(
                appModule
            )
        }
    }
}
