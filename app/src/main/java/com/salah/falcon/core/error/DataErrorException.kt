package com.salah.falcon.core.error

class DataErrorException(val error: DataError, cause: Throwable? = null) : Exception(cause)
