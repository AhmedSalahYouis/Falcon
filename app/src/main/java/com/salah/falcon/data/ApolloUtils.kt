package com.salah.falcon.data

import com.apollographql.apollo3.exception.ApolloException
import com.salah.falcon.data.model.DataSourceException


@Throws(DataSourceException::class)
suspend fun <ResponseData> safeApolloRequest(
    requestBlock: suspend () -> ResponseData,
): ResponseData {
    try {
        return requestBlock.invoke()
    } catch (apolloException: ApolloException) {
        throw DataSourceException(apolloException)
    }
}
