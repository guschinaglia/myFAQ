package com.myfaq.server.core.rest

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver
import com.myfaq.server.core.exception.handlers.LowerCaseClassNameResolver
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import java.time.LocalDateTime

@Suppress("KDocUnresolvedReference", "unused")
@JsonTypeInfo(
    include = JsonTypeInfo.As.WRAPPER_OBJECT,
    use = JsonTypeInfo.Id.CUSTOM,
    property = "error",
    visible = true
)
@JsonTypeIdResolver(
    LowerCaseClassNameResolver::class
)
class ApiError private constructor() {
    var status: HttpStatus? = null

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    val timestamp: LocalDateTime = LocalDateTime.now()
    var message: String? = null
    var debugMessage: String? = null
    private var subErrors: MutableList<ApiSubError>? = null

    constructor(status: HttpStatus?) : this() {
        this.status = status
    }

    constructor(status: HttpStatus?, ex: Throwable) : this() {
        this.status = status
        message = "Unexpected error"
        debugMessage = ex.localizedMessage
    }

    constructor(status: HttpStatus?, message: String?, ex: Throwable) : this() {
        this.status = status
        this.message = message
        debugMessage = ex.localizedMessage
    }

    private fun addSubError(subError: ApiSubError) {
        if (subErrors == null) {
            subErrors = ArrayList()
        }
        subErrors!!.add(subError)
    }

    private fun addValidationError(obj: String, field: String, rejectedValue: Any, message: String) {
        addSubError(ApiValidationError(obj, field, rejectedValue, message))
    }

    private fun addValidationError(obj: String, message: String) {
        addSubError(ApiValidationError(obj, message))
    }

    private fun addValidationError(fieldError: FieldError) {
        this.addValidationError(
            fieldError.objectName,
            fieldError.field,
            fieldError.rejectedValue!!,
            fieldError.defaultMessage!!
        )
    }

    fun addValidationErrors(fieldErrors: List<FieldError?>) {
        fieldErrors.forEach { fieldError: FieldError? -> this.addValidationError(fieldError!!) }
    }

    private fun addValidationError(objectError: ObjectError) {
        this.addValidationError(
            objectError.objectName,
            objectError.defaultMessage!!
        )
    }

    fun addValidationError(globalErrors: List<ObjectError?>) {
        globalErrors.forEach { objectError: ObjectError? -> this.addValidationError(objectError!!) }
    }
    /**
     * Utility method for adding error of ConstraintViolation. Usually when a @Validated validation fails.
     *
     * @param cv the ConstraintViolation
     */
    //    private void addValidationError(ConstraintViolation<?> cv) {
    //        this.addValidationError(
    //                cv.getRootBeanClass().getSimpleName(),
    //                ((PathImpl) cv.getPropertyPath()).getLeafNode().asString(),
    //                cv.getInvalidValue(),
    //                cv.getMessage());
    //    }
    //
    //    public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
    //        constraintViolations.forEach(this::addValidationError);
    //    }
}