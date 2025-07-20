package com.salah.falcon.data.mapper

import com.salah.falcon.data.model.MapperException

abstract class Mapper<From, To> {

    @Throws(MapperException::class)
    abstract fun map(from: From): To
}
