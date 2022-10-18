package com.myfaq.server.core.exception.handlers

import com.myfaq.server.core.exception.AddNewNotAllowed
import com.myfaq.server.core.exception.NotFoundException
import com.myfaq.server.core.rest.ApiError
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.dao.DuplicateKeyException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.http.converter.HttpMessageNotWritableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.function.Consumer

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler() {

    var log: Logger = LoggerFactory.getLogger(RestExceptionHandler::class.java)

    /**
     * Handle MissingServletRequestParameterException. Triggered when a 'required' request parameter is missing.
     *
     * @param ex      MissingServletRequestParameterException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    override fun handleMissingServletRequestParameter(
        ex: MissingServletRequestParameterException, headers: HttpHeaders,
        status: HttpStatus, request: WebRequest
    ): ResponseEntity<Any> {
        val error: String = ex.parameterName + " parameter is missing"
        return buildResponseEntity(ApiError(HttpStatus.BAD_REQUEST, error, ex))
    }

    override fun handleHttpMediaTypeNotSupported(
        ex: HttpMediaTypeNotSupportedException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val builder = StringBuilder()
        builder.append(ex.contentType)
        builder.append(" media type is not supported. Supported media types are ")
        ex.supportedMediaTypes.forEach(Consumer { t: MediaType? -> builder.append(t).append(", ") })
        return buildResponseEntity(
            ApiError(
                HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                builder.substring(0, builder.length - 2),
                ex
            )
        )
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val apiError = ApiError(HttpStatus.BAD_REQUEST)
        apiError.message = "Validation error"
        apiError.addValidationErrors(ex.bindingResult.fieldErrors)
        apiError.addValidationError(ex.bindingResult.globalErrors)
        return buildResponseEntity(apiError)
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val servletWebRequest: ServletWebRequest = request as ServletWebRequest
        log.info("${servletWebRequest.httpMethod} to ${servletWebRequest.request.servletPath}")
        val error = "Malformed JSON request"
        return buildResponseEntity(ApiError(HttpStatus.BAD_REQUEST, error, ex))
    }

    override fun handleHttpMessageNotWritable(
        ex: HttpMessageNotWritableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val error = "Error writing JSON output"
        return buildResponseEntity(ApiError(HttpStatus.INTERNAL_SERVER_ERROR, error, ex))
    }

    override fun handleNoHandlerFoundException(
        ex: NoHandlerFoundException, headers: HttpHeaders, status: HttpStatus, request: WebRequest
    ): ResponseEntity<Any> {
        val apiError = ApiError(HttpStatus.BAD_REQUEST)
        apiError.message = "Could not find the ${ex.httpMethod} method for URL ${ex.requestURL}"
        apiError.debugMessage = ex.message
        return buildResponseEntity(apiError)
    }

    // HANDLERS
    @ExceptionHandler(NotFoundException::class)
    protected fun handleNotFoundError(ex: NotFoundException, request: WebRequest?): ResponseEntity<Any> {
        return buildError(HttpStatus.NOT_FOUND, ex.statusText, ex)
    }

    @ExceptionHandler(AddNewNotAllowed::class)
    protected fun handleAddNewNotAllowed(ex: AddNewNotAllowed, request: WebRequest?): ResponseEntity<Any> {
        return buildError(HttpStatus.BAD_REQUEST, ex.statusText, ex)
    }

    /**
     * Handle Exception, handle generic Exception.class
     *
     * @param ex the Exception
     * @return the ApiError object
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    protected fun handleMethodArgumentTypeMismatch(
        ex: MethodArgumentTypeMismatchException,
        request: WebRequest?
    ): ResponseEntity<Any> {
        return buildError(
            HttpStatus.BAD_REQUEST,
            "The parameter ${ex.name} of value ${ex.value} could not be converted to type ${ex.requiredType!!.simpleName}",
            ex
        )
    }

    @ExceptionHandler(DuplicateKeyException::class)
    protected fun handleDuplicateKey(ex: DuplicateKeyException, request: WebRequest?): ResponseEntity<Any> {
        return buildError(HttpStatus.CONFLICT, "Duplicated value exists: ${ex.message}", ex)
    }

    private fun buildError(status: HttpStatus, message: String, ex: Exception): ResponseEntity<Any> {
        val apiError = ApiError(status)
        apiError.message = message
        apiError.debugMessage = ex.message
        return buildResponseEntity(apiError)
    }

    private fun buildResponseEntity(apiError: ApiError): ResponseEntity<Any> {
        return ResponseEntity<Any>(apiError, apiError.status!!)
    }
}