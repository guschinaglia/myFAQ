package com.myfaq.server.core.exception

import com.myfaq.server.common.messages.CommonMessages
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpClientErrorException

class MissingMandatoryException : HttpClientErrorException {
    constructor() : super(HttpStatus.NOT_FOUND) {}
    constructor(entity: String, field: String) : super(
        HttpStatus.BAD_REQUEST,
        CommonMessages.missingMandatory(entity, field)
    )
}