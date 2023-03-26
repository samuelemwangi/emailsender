package app.emailsender.presentation.exceptions

import app.emailsender.application.core.GetItemQueryHandler
import app.emailsender.application.core.errors.queries.geterror.GetErrorQuery
import app.emailsender.application.core.errors.viewmodels.ErrorViewModel
import app.emailsender.application.exceptions.DatabaseOperationException
import app.emailsender.application.exceptions.InvalidOperationException
import app.emailsender.application.exceptions.NoRecordException
import app.emailsender.application.exceptions.RecordExistsException
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.core.annotation.Order
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.server.MethodNotAllowedException
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.ServerWebInputException
import org.springframework.web.server.WebExceptionHandler
import reactor.core.publisher.Mono
import java.util.*


@Component
@Order(-2)
class RestExceptionHandler(
    private val getErrorQueryHandler: GetItemQueryHandler<GetErrorQuery, ErrorViewModel>,
    private val objectMapper: ObjectMapper
) : WebExceptionHandler {


    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        exchange.response.headers.contentType = MediaType.APPLICATION_JSON
        var errorMessage: String? = null

        if (ex is RecordExistsException) {
            exchange.response.statusCode = HttpStatus.CONFLICT
        } else if (ex is NoRecordException) {
            exchange.response.statusCode = HttpStatus.NOT_FOUND
        } else if (ex is InvalidOperationException) {
            exchange.response.statusCode = HttpStatus.NOT_ACCEPTABLE
        } else if (ex is WebExchangeBindException) {
            exchange.response.statusCode = HttpStatus.BAD_REQUEST
            errorMessage = getValidationErrors(ex)
        } else if (ex is ServerWebInputException) {
            exchange.response.statusCode = HttpStatus.BAD_REQUEST
            errorMessage = sanitizeWebInputException(exchange.request, ex)
        } else if (ex is MethodNotAllowedException) {
            exchange.response.statusCode = HttpStatus.METHOD_NOT_ALLOWED
        } else if (ex is ResponseStatusException) {
            try {
                exchange.response.statusCode = HttpStatus.valueOf(ex.rawStatusCode)
            } catch (e: Exception) {
                exchange.response.statusCode = HttpStatus.INTERNAL_SERVER_ERROR
            }
        } else if (ex is DatabaseOperationException) {
            exchange.response.statusCode = HttpStatus.INTERNAL_SERVER_ERROR
        } else {
            exchange.response.statusCode = HttpStatus.INTERNAL_SERVER_ERROR
        }
        errorMessage = errorMessage ?: ex.message



        return exchange
            .response
            .writeWith(transformError(errorMessage!!, exchange))
    }

    private fun sanitizeWebInputException(httpRequest: ServerHttpRequest, ex: ServerWebInputException): String? {
        var message = ex.reason
        val cause = ex.cause
        val defaultMessage = "A JSON decoding error occurred. Kindly check your request"
        if (cause != null) {
            val causeMessage = cause.message
            val causeMessageCaps = causeMessage!!.uppercase(Locale.getDefault())
            if (causeMessageCaps.contains("DECODING") && causeMessageCaps.contains("JSON")) {
                try {
                    val errorMessage = causeMessage.substring(0, causeMessage.lastIndexOf("; nested exception is"))
                    message =
                        if (errorMessage.lastIndexOf("DecodingException:") == -1) errorMessage else errorMessage.substring(
                            errorMessage.lastIndexOf("DecodingException:")
                        )
                    if (message.contains("Cannot deserialize value of type")) {
                        message = ("JSON decoding error: Cannot deserialize value"
                                + message.substring(message.indexOf("` from") + 1))
                    } else if (message.contains("JSON decoding error:") && (message.contains("`com.") || message.contains(
                            "io."
                        ))
                    ) message = defaultMessage
                } catch (e: Exception) {
                    message = defaultMessage
                }
            }
        }
        return message
    }

    private fun getValidationErrors(ex: WebExchangeBindException): String {
        val errors: MutableList<String> = ArrayList()
        for (error in ex.fieldErrors) {
            errors.add(error.field + ": " + error.defaultMessage)
        }
        for (error in ex.bindingResult.globalErrors) {
            errors.add(error.objectName + ": " + error.defaultMessage)
        }
        return java.lang.String.join(", ", errors)
    }

    private fun transformError(message: String, serverWebExchange: ServerWebExchange): Mono<DataBuffer> {
        val errorViewModel =
            getErrorQueryHandler
                .getItem(
                    GetErrorQuery(
                        message.substring(message.indexOf(":") + 1).trim(),
                        serverWebExchange.response.statusCode!!
                    )
                )

        val bufferFactory = serverWebExchange.response.bufferFactory()

        return try {
            Mono.just(bufferFactory.wrap(objectMapper.writeValueAsBytes(errorViewModel.toFuture().get())))
        } catch (e: JsonProcessingException) {
            Mono.just(bufferFactory.wrap("".toByteArray()))
        }
    }
}
