package com.tua.wanchalerm.example.product.handler

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.tua.wanchalerm.example.product.constant.ExceptionConstant.BAD_REQUEST
import com.tua.wanchalerm.example.product.constant.ExceptionConstant.DEFAULT_MESSAGE
import com.tua.wanchalerm.example.product.constant.ExceptionConstant.FIELD_IS_INVALID
import com.tua.wanchalerm.example.product.constant.ExceptionConstant.FIELD_IS_MISSING
import com.tua.wanchalerm.example.product.exception.BusinessException
import com.tua.wanchalerm.example.product.exception.DataNotFoundException
import com.tua.wanchalerm.example.product.exception.InputValidationException
import com.tua.wanchalerm.example.product.extension.camelToSnake
import com.tua.wanchalerm.example.product.model.common.ResponseStatus
import jakarta.validation.ConstraintViolationException
import org.apache.commons.lang3.StringUtils
import org.apache.http.entity.ContentType
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpMediaTypeException
import org.springframework.web.bind.*
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import reactor.core.publisher.Mono
import java.util.*

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(
        JsonParseException::class,
        HttpMediaTypeException::class,
        HttpMessageNotReadableException::class,
        MissingRequestHeaderException::class,
        ServletRequestBindingException::class,
        MethodArgumentTypeMismatchException::class
    )
    fun handleJsonParseException(ex: Exception): Mono<ResponseEntity<ResponseStatus>> {
        val description = getDescription(ex)
        val responseStatus = ResponseStatus(BAD_REQUEST, description)
        return Mono.just(
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
                .body(responseStatus)
        )
    }

    @ExceptionHandler(
        MethodArgumentNotValidException::class,
        ConstraintViolationException::class
    )
    fun handleBadRequest(ex: Exception): Mono<ResponseEntity<ResponseStatus>> {
        val errorMap = TreeMap<String, String>()

        if (ex is MethodArgumentNotValidException) {
            ex.bindingResult.fieldErrors.forEach { error ->
                val errorDescription = if (error.rejectedValue == null) FIELD_IS_MISSING else FIELD_IS_INVALID
                error.field.camelToSnake()
                    .let { errorMap[it] = String.format(errorDescription, it) }
            }

            ex.bindingResult.globalErrors.forEach { error ->
                val errorDescription =
                    if (error.defaultMessage?.contains("null")!!) FIELD_IS_MISSING else FIELD_IS_INVALID
                error.objectName.camelToSnake().let {
                    errorMap[it] = String.format(errorDescription, it)
                }
            }
        }

        (ex as? ConstraintViolationException)?.constraintViolations?.forEach { error ->
            val errorDescription =
                if (null == error.invalidValue) FIELD_IS_MISSING else FIELD_IS_INVALID
            val paths =
                StringUtils.split(error.propertyPath.toString(), ".")
            val fieldNameSnakeCase: String = paths[paths.size - 1].camelToSnake()
            errorMap[fieldNameSnakeCase] = String.format(errorDescription, fieldNameSnakeCase)
        }

        return Mono.just(
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
                .body(ResponseStatus(BAD_REQUEST, errorMap.firstEntry().value))
        )

    }

    private fun getDescription(exception: Exception): String {

        return when {
            exception.cause is JsonMappingException && (exception.cause as JsonMappingException).cause is InputValidationException -> {
                val cause = exception.cause as JsonMappingException
                String.format(FIELD_IS_INVALID, referencesToFields(cause.path))
            }
            exception.cause is InvalidFormatException -> {
                val cause = exception.cause as InvalidFormatException
                String.format(FIELD_IS_INVALID, referencesToFields(cause.path))
            }
            exception is MissingServletRequestParameterException -> {
                String.format(FIELD_IS_MISSING, exception.parameterName)
            }
            exception is MissingPathVariableException -> {
                String.format(FIELD_IS_MISSING, exception.variableName)
            }
            exception is MethodArgumentTypeMismatchException -> {
                String.format(FIELD_IS_INVALID, exception.name)
            }
            else -> DEFAULT_MESSAGE
        }

    }

    private fun referencesToFields(references: List<JsonMappingException.Reference>): String {
        val sb = StringBuilder()
        for (ref in references) {
            if (ref.fieldName == null) {
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
        return Mono.just(
            ResponseEntity.status(ex.httpStatus)
                .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())
                .body(ResponseStatus(ex.message, ex.description))
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