package com.myfaq.server.core.rest

data class ApiValidationError(
    var obj: String, var field: String, var rejectedValue: Any?, var message: String
) : ApiSubError() {
    constructor(
        obj: String,
        message: String
    ) : this(
        obj,
        field = "",
        rejectedValue = null,
        message
    )
}
