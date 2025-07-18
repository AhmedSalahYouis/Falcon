package com.salah.falcon.app

import android.app.Application
import com.salah.falcon.BuildConfig
import com.salah.falcon.app.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class FalconApp : Application() {

    companion object {
        private const val DEBUG_TAG = "Debug"
    }

    override fun onCreate() {
        super.onCreate()

        initDi()
        initLoggingTools()
    }

    private fun initLoggingTools() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.tag(DEBUG_TAG)
        }

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
