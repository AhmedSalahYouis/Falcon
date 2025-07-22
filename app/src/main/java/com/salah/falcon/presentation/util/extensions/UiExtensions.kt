package com.salah.falcon.presentation.util.extensions

import com.salah.falcon.R
import com.salah.falcon.core.error.DataError
import com.salah.falcon.presentation.util.UiText

fun DataError.toUiText(): UiText{

    return when(this){
        DataError.Network.NETWORK_EXCEPTION -> UiText.StringResource(R.string.error_network_exception)
        DataError.Network.NETWORK_UNAVAILABLE -> UiText.StringResource(R.string.error_network_unavailable)
        DataError.Network.REQUEST_TIMEOUT -> UiText.StringResource(R.string.error_timeout)
        DataError.Network.HTTP_ERROR -> UiText.StringResource(R.string.error_http)
        DataError.Network.PARSING_ERROR -> UiText.StringResource(R.string.error_parse)
        DataError.Network.UNEXPECTED_ERROR -> UiText.StringResource(R.string.unexpected_error)
    }

}