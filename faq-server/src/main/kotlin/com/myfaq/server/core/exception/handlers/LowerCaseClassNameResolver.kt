package com.myfaq.server.core.exception.handlers

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase
import java.util.*

class LowerCaseClassNameResolver : TypeIdResolverBase() {
    override fun idFromValue(value: Any): String {
        return value.javaClass.simpleName.lowercase(Locale.getDefault())
    }

    override fun idFromValueAndType(value: Any, suggestedType: Class<*>?): String {
        return idFromValue(value)
    }

    override fun getMechanism(): JsonTypeInfo.Id {
        return JsonTypeInfo.Id.CUSTOM
    }
}