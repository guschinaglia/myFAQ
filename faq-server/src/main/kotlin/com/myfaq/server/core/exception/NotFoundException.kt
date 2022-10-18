package com.myfaq.server.core.exception

import com.myfaq.server.common.messages.CommonMessages
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpClientErrorException

class NotFoundException : HttpClientErrorException {

    constructor() : super(
        HttpStatus.NOT_FOUND
    )

    constructor(statusText: String) : super(
        HttpStatus.NOT_FOUND,
    )

    constructor(entity: String, id: Long) : super(
        HttpStatus.NOT_FOUND,
        CommonMessages.entityNotFound(entity, id.toString())
    )

}