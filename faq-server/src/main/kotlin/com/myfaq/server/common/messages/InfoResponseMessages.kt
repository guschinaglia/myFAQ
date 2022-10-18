package com.myfaq.server.common.messages

object InfoResponseMessages {

    fun notFound(value: String): String {
        return "Nothing was found with Identifier: [ $value ]";
    }
}