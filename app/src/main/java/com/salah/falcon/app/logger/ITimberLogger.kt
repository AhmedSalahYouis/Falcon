package com.salah.falcon.app.logger

interface ITimberLogger {
    fun logError(
        message: String,
        exception: Exception? = null,
    )
}
