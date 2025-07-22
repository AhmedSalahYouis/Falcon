package com.salah.falcon.core.error

import com.apollographql.apollo3.exception.ApolloHttpException
import com.apollographql.apollo3.exception.ApolloNetworkException
import com.apollographql.apollo3.exception.ApolloParseException
import com.salah.falcon.data.model.MapperException

private const val TIMEOUT = "timeout"

private const val NO_NETWORK = "Unable to resolve host"

class DataErrorProvider : IDataErrorProvider {

    override fun fromThrowable(throwable: Throwable): DataError.Network {
        return when {
            throwable.message?.contains(NO_NETWORK) == true -> {
                DataError.Network.NETWORK_UNAVAILABLE
            }

            throwable.message?.contains(TIMEOUT, ignoreCase = true) == true -> {
                DataError.Network.REQUEST_TIMEOUT
            }

            throwable is ApolloHttpException -> {
                DataError.Network.HTTP_ERROR
            }
            
            throwable is MapperException -> {
                DataError.Network.PARSING_ERROR
            }

            throwable is ApolloNetworkException -> {
                DataError.Network.NETWORK_EXCEPTION
            }

            throwable is ApolloParseException -> {
                DataError.Network.PARSING_ERROR
            }

            else -> {
                DataError.Network.UNEXPECTED_ERROR
            }
        }
    }
}