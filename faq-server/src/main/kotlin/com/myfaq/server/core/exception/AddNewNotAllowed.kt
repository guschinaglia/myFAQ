package com.myfaq.server.core.exception

import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpClientErrorException

class AddNewNotAllowed(statusText: String) : HttpClientErrorException(HttpStatus.BAD_REQUEST, statusText)