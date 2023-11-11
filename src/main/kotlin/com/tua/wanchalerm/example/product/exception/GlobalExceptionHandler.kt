package com.tua.wanchalerm.example.product.exception

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.tua.wanchalerm.example.product.constant.ExceptionConstant
import com.tua.wanchalerm.example.product.constant.ExceptionConstant.DEFAULT_MESSAGE
import com.tua.wanchalerm.example.product.constant.ExceptionConstant.FIELD_IS_INVALID
import com.tua.wanchalerm.example.product.constant.ExceptionConstant.FIELD_IS_MISSING
import com.tua.wanchalerm.example.product.model.common.ResponseStatus
import org.apache.http.entity.ContentType
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpMediaTypeException
import org.springframework.web.bind.MissingPathVariableException
import org.springframework.web.bind.MissingRequestHeaderException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import reactor.core.publisher.Mono

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(
        JsonParseException::class,
        HttpMediaTypeException::class,
        HttpMessageNotReadableException::class,
        MissingRequestHeaderException::class,
        ServletRequestBindingException::class,
        MethodArgumentTypeMismatchException::class
    )
    fun handleJsonParseException(ex: Exception): Mono<ResponseEntity<ResponseStatus>>  {
        val description = getDescription(ex)
        val responseStatus = ResponseStatus(ExceptionConstant.BAD_REQUEST, description)
        return Mono.just(
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
                .body(responseStatus)
        )
    }

    private fun getDescription(exception: Exception): String {
        return if (exception.cause is JsonMappingException
            && (exception.cause as JsonMappingException).cause is InputValidationException
        ) {
            val cause = exception.cause as JsonMappingException
            String.format(FIELD_IS_INVALID, referencesToFields(cause.getPath()))
        } else if (exception.cause is InvalidFormatException) {
            val cause = exception.cause as InvalidFormatException
            String.format(FIELD_IS_INVALID, referencesToFields(cause.getPath()))
        } else if (exception is MissingServletRequestParameterException) {
            String.format(FIELD_IS_MISSING, exception.parameterName)
        } else if (exception is MissingPathVariableException) {
            String.format(FIELD_IS_MISSING, exception.variableName)
        } else if (exception is MethodArgumentTypeMismatchException) {
            String.format(FIELD_IS_INVALID, exception.name)
        } else {
            DEFAULT_MESSAGE
        }
    }

    private fun referencesToFields(references: List<JsonMappingException.Reference>): String {
        val sb = StringBuilder()
        for (ref in references) {
            if (null == ref.fieldName) {
                sb.deleteCharAt(sb.length - 1)
                    .append("[")
                    .append(ref.index)
                    .append("]")
            } else {
                sb.append(ref.fieldName)
            }
            sb.append(".")
        }
        return sb.substring(0, sb.length - 1)
    }

    @ExceptionHandler(BusinessException::class)
    @ResponseBody
    fun handleBusinessException(ex: BusinessException): Mono<ResponseEntity<ResponseStatus>> {
        val responseStatus = ResponseStatus(ex.message, ex.description)
        return Mono.just(
            ResponseEntity.status(ex.httpStatus)
                .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
                .body(responseStatus)
        )
    }

    @ExceptionHandler(InputValidationException::class)
    @ResponseBody
    fun handleInputValidationException(ex: InputValidationException): Mono<ResponseEntity<ResponseStatus>> {
        val responseStatus = ResponseStatus(ex.message, ex.description)
        return Mono.just(
            ResponseEntity.status(ex.httpStatus)
                .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
                .body(responseStatus)
        )
    }

    @ExceptionHandler(DataNotFoundException::class)
    @ResponseBody
    fun handleDataNotFoundException(ex: DataNotFoundException): Mono<ResponseEntity<ResponseStatus>> {
        val responseStatus = ResponseStatus(ex.message, ex.description)
        return Mono.just(
            ResponseEntity.status(ex.httpStatus)
                .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
                .body(responseStatus)
        )
    }
}