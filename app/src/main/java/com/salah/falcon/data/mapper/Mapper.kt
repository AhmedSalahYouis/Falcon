package com.salah.falcon.data.mapper

import com.salah.falcon.data.model.MapperException

interface Mapper<From, To> {

    @Throws(MapperException::class)
    fun map(from: From): To
}
