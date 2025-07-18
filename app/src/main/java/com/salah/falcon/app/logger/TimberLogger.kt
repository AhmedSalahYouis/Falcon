package com.salah.falcon.app.logger

import timber.log.Timber

class TimberLogger : ITimberLogger {
    companion object {
        private const val APP_TAG = "FalconApp"
    }

    override fun logError(message: String, exception: Exception?) {
        Timber.e(APP_TAG, message, exception)
    }
}
