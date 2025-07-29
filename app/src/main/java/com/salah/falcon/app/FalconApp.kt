package com.salah.falcon.app

import android.app.Application
import com.salah.falcon.BuildConfig
import com.salah.falcon.app.di.appModule
import com.salah.falcon.app.di.dataModule
import com.salah.falcon.app.di.domainModule
import com.salah.falcon.app.di.errorModule
import com.salah.falcon.app.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.option.viewModelScopeFactory
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
            // If you mistakenly define the same dependency multiple times,
            // Koin will fail fast during startup,
            // making it easier to identify and fix the issue
            allowOverride(false)

            modules(
                appModule,
                domainModule,
                dataModule,
                errorModule,
                viewModelModule
            )
            // Enable scope-based ViewModel factory
            options(viewModelScopeFactory())

        }
    }
}
