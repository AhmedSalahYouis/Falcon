package com.salah.falcon.core.error

sealed interface DataError: Error {

    enum class Network : DataError {
        NETWORK_EXCEPTION,
        NETWORK_UNAVAILABLE,
        REQUEST_TIMEOUT,
        HTTP_ERROR,
        PARSING_ERROR,
        UNEXPECTED_ERROR

    }
}
