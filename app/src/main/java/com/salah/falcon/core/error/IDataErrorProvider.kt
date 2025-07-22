package com.salah.falcon.core.error

interface IDataErrorProvider {
    fun fromThrowable(throwable: Throwable): DataError.Network
}